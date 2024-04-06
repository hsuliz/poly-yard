package dev.hsuliz.bookservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class BookServiceApp

fun main(args: Array<String>) {
    runApplication<BookServiceApp>(*args)
}
