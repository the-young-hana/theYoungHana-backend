package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@TypeInfo(name = "RedisCacheConfig", description = "Redis Cache 설정 클래스")
@Configuration
@EnableCaching
@Import(RedisConfig.class)
public class RedisCacheConfig {
    @MethodInfo(name = "redisCacheManager", description = "기본 Redis CacheManager를 설정합니다.")
    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()))
                        .entryTtl(Duration.ofHours(1L));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
                        redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @MethodInfo(name = "oidcCacheManager", description = "OIDC CacheManager를 설정합니다.")
    @Bean
    public CacheManager oidcCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()))
                        .entryTtl(Duration.ofDays(7L));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
                        redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
