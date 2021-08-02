package infrastructure.framework

import interfaces.controllers.BookController
import interfaces.controllers.BookController.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import javax.validation.ValidationException
import javax.validation.Validator

fun <T> Validator.validateAndThrow(obj: T, vararg groups: Class<*>?) {
    val errors = this.validate(obj, *groups)
    if (errors.size > 0) throw ValidationException(errors.joinToString { e -> "${e.propertyPath}: ${e.message}" })
}

@kotlin.OptIn(KoinApiExtension::class)
internal fun Routing.root() {
    val validator: Validator by inject()

    route("/books"){
        val bookController: BookController by inject()
        get("/") {
            call.respond( transactionWrapper { bookController.index() } )
        }
        get("/{id}") {
            val input = BookInput(call.parameters.getOrFail<Int>("id"))
            call.respond( transactionWrapper { bookController.get(input) } )
        }
        post("/new") {
            val input = call.receive<BookPostInput>()
            validator.validateAndThrow(input)
            call.respond( transactionWrapper { bookController.post(input) } )
        }
    }
}
