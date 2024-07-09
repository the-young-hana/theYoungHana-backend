package hana.common.config;

import hana.common.exception.TokenExpiredException;
import hana.common.exception.TokenHasUnknownMemberException;
import hana.common.vo.JwtToken;
import hana.member.domain.Member;
import hana.member.domain.MemberRepository;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final int accessTokenExpiration;
    private final int refreshTokenExpiration;
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    public JwtToken generateToken(Member member) {

        Claims claims = Jwts.claims();
        claims.put("memberIdx", member.getMemberIdx());

        long now = (new Date()).getTime();
        Date refreshTokenExpiresIn = new Date(now + refreshTokenExpiration);

        String refreshToken =
                Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(refreshTokenExpiresIn)
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return this.getAccessToken(refreshToken);
    }

    public JwtToken getAccessToken(String refreshToken) {
        this.validateToken(refreshToken);
        Member member = this.getMember(refreshToken);
        Claims claims = Jwts.claims();
        claims.put("memberIdx", member.getMemberIdx());
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpiration);

        String newAccessToken =
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(member.getMemberId())
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return JwtToken.builder().accessToken(newAccessToken).refreshToken(refreshToken).build();
    }

    public Member getMember(String token) {
        this.validateToken(token);
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        if (claims.get("memberIdx") == null) {
            throw new TokenHasUnknownMemberException();
        }
        Member member =
                memberRepository.findByMemberIdx(Long.valueOf(claims.get("memberIdx").toString()));
        if (member != null) {
            return member;
        }
        throw new TokenHasUnknownMemberException();
    }

    public Student getStudent(String token) {
        this.validateToken(token);
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        if (claims.get("memberIdx") == null) {
            throw new TokenHasUnknownMemberException();
        }
        return studentRepository.findByMember_MemberIdx(
                Long.valueOf(claims.get("memberIdx").toString()));
    }

    public boolean validateToken(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            throw new TokenExpiredException();
        }
    }

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration}") int accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") int refreshTokenExpiration,
            MemberRepository memberRepository,
            StudentRepository studentRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.memberRepository = memberRepository;
        this.studentRepository = studentRepository;
    }
}
