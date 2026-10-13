package uz.ttpu.booknest.core.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.ttpu.booknest.core.model.Book

@Composable
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
            BookCardContent(book, trailing)
        }
    } else {
        Card(modifier = modifier.fillMaxWidth()) {
            BookCardContent(book, trailing)
        }
    }
}

@Composable
private fun BookCardContent(
    book: Book,
    trailing: (@Composable () -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text(
                book.author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                formatPrice(book.price),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        trailing?.invoke()
    }
}

fun formatPrice(price: Int): String =
    "$" + (price / 100) + "." + (price % 100).toString().padStart(2, '0')

@Preview(showBackground = true)
@Composable
private fun BookCardPreview() {
    BookNestTheme {
        BookCard(book = Book("p1", "Kotlin in Action", "J. Jemerov", 3500))
    }
}
