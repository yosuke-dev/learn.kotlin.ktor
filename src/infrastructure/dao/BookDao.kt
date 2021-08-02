package infrastructure.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object BookTable : IntIdTable("books") {
    val title = text("title")
    val author = text("author")
}

class BookEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookEntity>(BookTable)
    var title by BookTable.title
    var author by BookTable.author
}