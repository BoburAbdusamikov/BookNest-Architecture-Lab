package uz.ttpu.booknest.feature.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import uz.ttpu.booknest.core.model.Book

sealed interface FavoritesContent {
    data object Loading : FavoritesContent
    data object Empty : FavoritesContent
    data class Books(val books: List<Book>) : FavoritesContent
}

class FavoritesViewState : FavoritesContract.View {
    var content: FavoritesContent by mutableStateOf<FavoritesContent>(FavoritesContent.Loading)
        private set

    override fun showLoading() {
        content = FavoritesContent.Loading
    }

    override fun showFavorites(books: List<Book>) {
        content = FavoritesContent.Books(books)
    }

    override fun showEmpty() {
        content = FavoritesContent.Empty
    }
}
