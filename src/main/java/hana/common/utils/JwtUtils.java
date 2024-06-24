package hana.common.utils;

import hana.common.dto.JwtToken;
import hana.member.domain.Member;
import hana.member.domain.MemberRepository;
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
public class JwtUtils {
    private final Key key;
    private final int accessTokenExpiration;
    private final int refreshTokenExpiration;
    private final MemberRepository memberRepository;

    public JwtUtils(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration}") int accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") int refreshTokenExpiration,
            MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.memberRepository = memberRepository;
    }

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

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Member getMember(String token) {
        this.validateToken(token);
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        if (claims.get("memberIdx") == null) {
            throw new RuntimeException("유저 정보가 없는 토큰입니다.");
        }
        Member member =
                memberRepository.findByMemberIdx(Long.valueOf(claims.get("memberIdx").toString()));
        if (member != null) {
            return member;
        }
        throw new RuntimeException("해당 아이디의 유저가 존재하지 않습니다.");
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
            throw new RuntimeException("만료된 토큰입니다.");
        }
    }
}
