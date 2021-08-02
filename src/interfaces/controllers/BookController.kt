package interfaces.controllers

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import usecase.BookService
import javax.validation.constraints.Size

@KoinApiExtension
class BookController : KoinComponent {
    private val logger: Logger = LoggerFactory.getLogger("MemoController")
    private val bookService: BookService by inject()

    data class BookInput(val bookId: Int)
    data class BookPostInput(@field:Size(max = 20) val title: String,@field:Size(max = 20)  val author: String)
    data class BookOutput(val id: Int, val title: String, val author: String)

    fun index(): List<BookOutput> {
        logger.info("BookController.index called.")
        val result : BookService.IndexOutput = bookService.getIndex()
        return result.books.map { BookOutput(it.id, it.title, it.author) }
    }

    fun get(input: BookInput): BookOutput {
        logger.info("BookController.get called.")
        val result : BookService.ByIdOutput = bookService.getById(BookService.ByIdInput(input.bookId))
        result.book ?: throw RuntimeException("Book is not found")
        return BookOutput(result.book.id, result.book.title, result.book.author)
    }

    fun post(input: BookPostInput): BookOutput {
        logger.info("BookController.post called.")
        val result: BookService.CreateOutput = bookService.create(BookService.CreateInput(input.title, input.author))
        return BookOutput(result.book.id, result.book.title, result.book.author)
    }
}