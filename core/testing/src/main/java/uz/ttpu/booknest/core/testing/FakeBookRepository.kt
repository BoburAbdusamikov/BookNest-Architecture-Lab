package uz.ttpu.booknest.core.testing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import uz.ttpu.booknest.core.model.Book
import uz.ttpu.booknest.data.books.BookRepository

val testBooks = listOf(
    Book("t1", "Test Book One", "Author One", 1000),
    Book("t2", "Test Book Two", "Author Two", 2000),
    Book("t3", "Test Book Three", "Author Three", 3000),
)

class FakeBookRepository(
    books: List<Book> = testBooks,
) : BookRepository {
    private val books = MutableStateFlow(books)
    private val favoriteIds = MutableStateFlow(emptySet<String>())

    override fun observeBooks(): Flow<List<Book>> = books.asStateFlow()

    override fun observeFavoriteIds(): Flow<Set<String>> = favoriteIds.asStateFlow()

    override suspend fun setFavorite(bookId: String, favorite: Boolean) {
        favoriteIds.update { if (favorite) it + bookId else it - bookId }
    }

    override suspend fun getBook(bookId: String): Book? =
        books.value.firstOrNull { it.id == bookId }
}
