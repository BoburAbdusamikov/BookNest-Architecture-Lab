package uz.ttpu.booknest

import android.app.Application
import uz.ttpu.booknest.di.AppContainer

class BookNestApplication : Application() {
    val container: AppContainer by lazy { AppContainer() }
}
