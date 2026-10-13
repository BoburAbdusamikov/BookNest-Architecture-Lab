package uz.ttpu.booknest.data.orders

import kotlinx.coroutines.delay

class FakeOrderRepository : OrderRepository {
    private var lastNumber = 1000

    override suspend fun placeOrder(bookId: String, email: String): Result<String> {
        delay(600)
        if (!email.contains("@") || !email.substringAfter("@").contains(".")) {
            return Result.failure(IllegalArgumentException("Please enter a valid e-mail address"))
        }
        lastNumber += 1
        return Result.success("BN-$lastNumber")
    }
}
