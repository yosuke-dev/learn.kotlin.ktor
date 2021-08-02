package usecase

import domain.models.Book
import interfaces.repositories.BookRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

@KoinApiExtension
class BookService : KoinComponent {
    private val bookRepository: BookRepository by inject()

    fun getIndex() : IndexOutput{
        return IndexOutput(bookRepository.getAll())
    }

    fun getById(input : ByIdInput) : ByIdOutput{
        return ByIdOutput(bookRepository.findById(input.id))
    }

    fun create(input : CreateInput) : CreateOutput{
        return CreateOutput(bookRepository.create(input.title, input.author))
    }

    data class ByIdInput(val id : Int)
    data class ByIdOutput(val book : Book?)
    data class CreateInput(val title: String, val author: String)
    data class CreateOutput(val book : Book)
    data class IndexOutput(val books : List<Book>)
}