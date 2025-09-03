package com.bookbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
class BookbookApplication

fun main(args: Array<String>) {
    val dbUrl = System.getenv("DATABASE_URL")
    println("DEBUG: DATABASE_URL is $dbUrl")
    runApplication<BookbookApplication>(*args) {
        addListeners(ApplicationListener<ApplicationEnvironmentPreparedEvent> { event ->
            println("DEBUG: Active Profiles: ${event.environment.activeProfiles.joinToString()}")
        })
    }
}