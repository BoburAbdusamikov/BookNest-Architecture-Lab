package uz.ttpu.booknest.feature.checkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uz.ttpu.booknest.core.designsystem.BookNestTheme
import uz.ttpu.booknest.core.model.Book

private val previewBook = Book("p1", "Kotlin in Action", "J. Jemerov", 3500)

@Preview(showBackground = true)
@Composable
private fun CheckoutFailedPreview() {
    BookNestTheme {
        CheckoutScreen(
            state = CheckoutState(
                isLoading = false,
                book = previewBook,
                email = "abc",
                errorMessage = "Please enter a valid e-mail address",
            ),
            onIntent = {},
            onFinished = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutFinishedPreview() {
    BookNestTheme {
        CheckoutScreen(
            state = CheckoutState(
                isLoading = false,
                book = previewBook,
                email = "a@b.uz",
                orderNumber = "BN-1001",
            ),
            onIntent = {},
            onFinished = {},
        )
    }
}
