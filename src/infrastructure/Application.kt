package infrastructure

import com.fasterxml.jackson.databind.SerializationFeature
import infrastructure.framework.koinModules
import infrastructure.framework.root
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import javax.validation.ValidationException

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    Database.connect(
        url = environment.config.property("app.database.url").getString(),
        user = environment.config.property("app.database.user").getString(),
        password = environment.config.property("app.database.password").getString(),
        driver = "com.mysql.cj.jdbc.Driver"
    )
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(Locations)
    install(Koin) { modules(koinModules) }
    install(StatusPages) {
        data class ErrorResponse(val message: String?)

        exception<NotFoundException> {
            call.respond(HttpStatusCode.NotFound, ErrorResponse(it.message))
        }
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<MissingRequestParameterException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<ParameterConversionException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<ValidationException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<DataConversionException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(it.message))
        }
    }
    routing {
        root()
    }
}