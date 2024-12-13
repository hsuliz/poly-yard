package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.model.Review
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

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
    return ReactiveAuditorAware<String> {
      ReactiveSecurityContextHolder.getContext()
          .map { it.authentication }
          .filter { it.isAuthenticated }
          .map {
            val jwt = it.principal as Jwt
            return@map jwt.getClaimAsString("preferred_username")
          }
          .switchIfEmpty(mono { "anonymous" })
    }
  }
}
