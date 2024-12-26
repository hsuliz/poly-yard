package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.util.currentUsernameMono
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
class R2dbcConfiguration(private val properties: R2dbcProperties) : AbstractR2dbcConfiguration() {

  @Bean
  @Lazy
  override fun connectionFactory(): ConnectionFactory {
    // Parse the URL: r2dbc:postgresql://host:port/database
    val (host, port, database) = parseR2dbcUrl(properties.url)

    return PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host(host)
            .port(port)
            .database(database)
            .username(properties.username)
            .password(properties.password)
            .codecRegistrar(
                EnumCodec.builder()
                    .withEnum("review_type", Review.Type::class.java)
                    .withEnum("resource_type", Review.Resource.Type::class.java)
                    .build())
            .build())
  }

  override fun getCustomConverters(): List<Any> {
    return listOf(
        ReviewTypeWritingConverter(),
        ReviewResourceTypeWritingConverter(),
        ReviewTypeReadingConverter(),
        ReviewResourceTypeReadingConverter())
  }

  @Bean
  fun reactiveAuditorAware(): ReactiveAuditorAware<String> {
    return ReactiveAuditorAware<String> { currentUsernameMono() }
  }

  private fun parseR2dbcUrl(url: String): Triple<String, Int, String> {
    val regex = Regex("""r2dbc:postgresql://([^:/]+):(\d+)/(\w+)""")
    val match = regex.find(url) ?: throw IllegalArgumentException("Invalid R2DBC URL format")
    val (host, port, database) = match.destructured
    return Triple(host, port.toInt(), database)
  }
}
