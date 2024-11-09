package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.entity.ReviewEntity
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class PostgresqlConfig : AbstractR2dbcConfiguration() {



  @Bean
  @Primary
  override fun connectionFactory(): ConnectionFactory {
    return PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .port(5433)
            .database("review")
            .username("postgres")
            .password("review")
            .codecRegistrar(
                EnumCodec.builder().withEnum("category", ReviewEntity.Category::class.java).build())
            .build())
  }

    override fun getCustomConverters(): List<Any> {
        return java.util.List.of<Any>(PostStatusWritingConverter(), PostStatusWritingConverter())
    }
}
