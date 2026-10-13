package uz.ttpu.booknest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.ttpu.booknest.di.AppContainer
import uz.ttpu.booknest.feature.catalog.CatalogRoute
import uz.ttpu.booknest.feature.checkout.CheckoutRoute
import uz.ttpu.booknest.feature.favorites.FavoritesRoute

object Routes {
    const val CATALOG = "catalog"
    const val FAVORITES = "favorites"
    const val CHECKOUT = "checkout/{bookId}"

    fun checkout(bookId: String) = "checkout/$bookId"
}

@Composable
fun BookNestNavHost(
    container: AppContainer,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.CATALOG,
        modifier = modifier,
    ) {
        composable(Routes.CATALOG) {
            CatalogRoute(
                repository = container.bookRepository,
                onBookClick = { navController.navigate(Routes.checkout(it)) },
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesRoute(
                repository = container.bookRepository,
                onBookClick = { navController.navigate(Routes.checkout(it)) },
            )
        }
        composable(
            route = Routes.CHECKOUT,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType }),
        ) {
            CheckoutRoute(
                bookRepository = container.bookRepository,
                orderRepository = container.orderRepository,
                onFinished = { navController.popBackStack() },
            )
        }
    }
}
