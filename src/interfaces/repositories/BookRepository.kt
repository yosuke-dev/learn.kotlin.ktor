package interfaces.repositories

import domain.models.Book

interface BookRepository {
    fun getAll() : List<Book>

    fun findById(id: Int) : Book?

    fun create(title: String, author: String) : Book
}