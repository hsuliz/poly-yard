package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.util.getCurrentUsernameMono
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing

@Configuration
@EnableR2dbcAuditing
class R2dbcConfiguration : AbstractR2dbcConfiguration() {

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
                EnumCodec.builder()
                    .withEnum("review_type", Review.Type::class.java)
                    .withEnum("resource_type", Review.Resource.Type::class.java)
                    .build())
            .build())
  }

  override fun getCustomConverters(): List<Any> {
    return listOf(CategoryWritingConverter(), ResourceWritingConverter())
  }

  @Bean
  fun reactiveAuditorAware(): ReactiveAuditorAware<String> {
    return ReactiveAuditorAware<String> { getCurrentUsernameMono() }
  }
}
