package interfaces.repositories

import domain.models.Book
import infrastructure.dao.BookEntity
import org.jetbrains.exposed.sql.SizedIterable

class BookRepositoryImpl : BookRepository {
    override fun getAll(): List<Book> {
        val bookEntities: SizedIterable<BookEntity> = BookEntity.all()
        return convertToBooks(bookEntities)
    }

    override fun findById(id: Int): Book? {
        val bookEntity: BookEntity? = BookEntity.findById(id)
        bookEntity ?: return null
        return convertToBook(bookEntity)
    }

    override fun create(title: String, author: String): Book {
        val bookEntity: BookEntity = BookEntity.new {
            this.title = title
            this.author = author
        }
        return convertToBook(bookEntity)
    }

    private fun convertToBook(bookEntity: BookEntity) : Book {
        return Book(bookEntity.id.value, bookEntity.title, bookEntity.author)
    }

    private fun convertToBooks(bookEntities: SizedIterable<BookEntity>) : List<Book> {
        return bookEntities.map { Book(it.id.value, it.title, it.author) }
    }
}