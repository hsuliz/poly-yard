package dev.hsuliz.polyyard.gateway.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class ReviewRequestChecker(
    private val bookWebClient: WebClient,
    private val objectMapper: ObjectMapper
) : GatewayFilter {

  override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
    return DataBufferUtils.join(exchange.request.body).flatMap { dataBuffer ->
      val bodyBytes = ByteArray(dataBuffer.readableByteCount())
      dataBuffer.read(bodyBytes)
      DataBufferUtils.release(dataBuffer)
      val bodyString = String(bodyBytes, StandardCharsets.UTF_8)

      println("Request Body: $bodyString")

      val reviewRequest = objectMapper.readValue(bodyString, ReviewRequest::class.java)
      if (reviewRequest.type != "BOOK") {
        println("Invalid type: ${reviewRequest.type}")
        exchange.response.statusCode = HttpStatus.BAD_REQUEST
        return@flatMap exchange.response.setComplete()
      }

      println("Valid type: ${reviewRequest.type}, Checking book...")

      checkBookExists(reviewRequest.resource.value).flatMap checkBook@{ bookExists ->
        if (!bookExists) {
          println("Book not found for ISBN: ${reviewRequest.resource.value}")
          exchange.response.statusCode = HttpStatus.NOT_FOUND
          return@checkBook exchange.response.setComplete()
        }

        println("Book found for ISBN: ${reviewRequest.resource.value}")

        val mutatedRequest = exchange.request.mutate().build()
        val cachedBody = Flux.just(toDataBuffer(bodyString))
        val decoratedRequest =
            object : ServerHttpRequestDecorator(mutatedRequest) {
              override fun getBody(): Flux<DataBuffer> = cachedBody
            }

        val mutatedExchange = exchange.mutate().request(decoratedRequest).build()
        chain.filter(mutatedExchange)
      }
    }
  }

  private fun checkBookExists(isbn: String): Mono<Boolean> {
    return bookWebClient.get().uri("/api/books/$isbn").exchangeToMono { response ->
      if (response.statusCode() == HttpStatus.OK) {
        Mono.just(true)
      } else if (response.statusCode() == HttpStatus.NOT_FOUND) {
        Mono.just(false)
      } else {
        response.createException().flatMap { Mono.error(it) }
      }
    }
  }

  private fun toDataBuffer(value: String): DataBuffer {
    val factory = org.springframework.core.io.buffer.DefaultDataBufferFactory.sharedInstance
    return factory.wrap(value.toByteArray(StandardCharsets.UTF_8))
  }
}
