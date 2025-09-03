package com.bookbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import java.sql.DatabaseMetaData
import javax.sql.DataSource
import kotlin.jvm.java

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

    val context: ConfigurableApplicationContext = runApplication<BookbookApplication>(*args)
    try {
        val dataSource = context.getBean(DataSource::class.java)
        val connection = dataSource.connection
        val metaData: DatabaseMetaData = connection.metaData
        val tables = metaData.getTables(null, null, "user_roles", null)
        if (tables.next()) {
            println("DEBUG: user_roles table EXISTS.")
        } else {
            println("DEBUG: user_roles table DOES NOT EXIST.")
        }
        connection.close()
    } catch (e: Exception) {
        println("DEBUG: Error checking for tables: ${e.message}")
    }
}