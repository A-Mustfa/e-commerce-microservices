//package org.ecommerce.ecommerce_service.config;
//
//import org.ecommerce.ecommerce_service.dto.ItemResponse;
//import org.ecommerce.ecommerce_service.models.Item;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
////@Configuration
////public class RedisConfig {
////
////    @Bean
////    @Primary
////    public RedisCacheManager redisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
////        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
////                .entryTtl(Duration.ofHours(1))
////                .disableCachingNullValues()
////                .serializeValuesWith(RedisSerializationContext.SerializationPair
////                        .fromSerializer(new Jackson2JsonRedisSerializer<>(ItemResponse.class)));
////        return RedisCacheManager.builder(redisConnectionFactory)
////                .cacheDefaults(redisCacheConfiguration).build();
////    }
////}
//@Configuration
//@EnableCaching
//public class RedisConfig {
//
//    @Bean
//    @Primary
//    public RedisCacheManager redisCacheManager(
//            RedisConnectionFactory connectionFactory) {
//
//        RedisCacheConfiguration config =
//                RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofHours(1))
//                        .disableCachingNullValues()
//                        .serializeKeysWith(
//                                RedisSerializationContext.SerializationPair
//                                        .fromSerializer(new StringRedisSerializer()))
//                        .serializeValuesWith(
//                                RedisSerializationContext.SerializationPair
//                                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }
//}
