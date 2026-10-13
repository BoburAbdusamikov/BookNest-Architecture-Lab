package uz.ttpu.booknest.data.books

import kotlinx.coroutines.flow.Flow
import uz.ttpu.booknest.core.model.Book

interface BookRepository {
    fun observeBooks(): Flow<List<Book>>
    fun observeFavoriteIds(): Flow<Set<String>>
    suspend fun setFavorite(bookId: String, favorite: Boolean)
    suspend fun getBook(bookId: String): Book?
}
