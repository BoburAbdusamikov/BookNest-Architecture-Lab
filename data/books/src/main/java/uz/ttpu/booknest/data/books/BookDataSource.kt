package uz.ttpu.booknest.data.books

import uz.ttpu.booknest.core.model.Book

class BookDataSource {
    fun fetchBooks(): List<Book> = listOf(
        Book("b1", "Kotlin in Action", "J. Jemerov", 3500),
        Book("b2", "Clean Architecture", "R. Martin", 3200),
        Book("b3", "Clean Code", "R. Martin", 2900),
        Book("b4", "Effective Java", "J. Bloch", 4100),
        Book("b5", "The Pragmatic Programmer", "D. Thomas", 3800),
        Book("b6", "Refactoring", "M. Fowler", 3600),
        Book("b7", "Design Patterns", "E. Gamma", 4500),
        Book("b8", "Head First Android Development", "D. Griffiths", 3300),
    )
}
