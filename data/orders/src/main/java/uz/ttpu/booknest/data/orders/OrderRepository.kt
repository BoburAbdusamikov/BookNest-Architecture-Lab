package uz.ttpu.booknest.data.orders

interface OrderRepository {
    suspend fun placeOrder(bookId: String, email: String): Result<String>
}
