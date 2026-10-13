package uz.ttpu.booknest.di

import uz.ttpu.booknest.data.books.BookRepository
import uz.ttpu.booknest.data.books.InMemoryBookRepository
import uz.ttpu.booknest.data.orders.FakeOrderRepository
import uz.ttpu.booknest.data.orders.OrderRepository

class AppContainer {
    val bookRepository: BookRepository = InMemoryBookRepository()
    val orderRepository: OrderRepository = FakeOrderRepository()
}
