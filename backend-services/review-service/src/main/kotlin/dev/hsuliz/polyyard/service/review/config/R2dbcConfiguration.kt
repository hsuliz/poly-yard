package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.util.getCurrentUsernameMono
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
class R2dbcConfiguration(private val connectionFactory: ConnectionFactory) :
    AbstractR2dbcConfiguration() {

  override fun connectionFactory(): ConnectionFactory {
    return connectionFactory()
  }

  override fun getCustomConverters(): List<Any> {
    return listOf(CategoryWritingConverter(), ResourceWritingConverter())
  }

  @Bean
  fun reactiveAuditorAware(): ReactiveAuditorAware<String> {
    return ReactiveAuditorAware<String> { getCurrentUsernameMono() }
  }
}
