package uz.ttpu.booknest.data.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import uz.ttpu.booknest.core.model.Book

class InMemoryBookRepository(
    dataSource: BookDataSource = BookDataSource(),
) : BookRepository {
    private val books = MutableStateFlow(dataSource.fetchBooks())
    private val favoriteIds = MutableStateFlow(emptySet<String>())

    override fun observeBooks(): Flow<List<Book>> = books.asStateFlow()

    override fun observeFavoriteIds(): Flow<Set<String>> = favoriteIds.asStateFlow()

    override suspend fun setFavorite(bookId: String, favorite: Boolean) {
        favoriteIds.update { if (favorite) it + bookId else it - bookId }
    }

    override suspend fun getBook(bookId: String): Book? =
        books.value.firstOrNull { it.id == bookId }
}
