# Architectural Design Patterns — Answers

## Part A — MVVM

### Task 1
- Slide 9 → BookNest: data/api = `BookDataSource` (:data:books), data/model = `Book` (:core:model), data/repository = `BookRepository` / `InMemoryBookRepository` (:data:books, :data:orders), presentation/viewmodel = `CatalogViewModel`, presentation/view = `CatalogScreen` / `CatalogRoute` (:feature:catalog), di = `AppContainer` (:app).
- `combine` merges `observeBooks()` and `observeFavoriteIds()`. "Favorite" is a UI fact about this user, not a property of the book, so it belongs in `CatalogItem` and `Book` stays a clean model.
- If the state were public, the View could write `viewModel._uiState.value = ...`.
- The interface gives testability: the repository can be replaced with a fake.

### Task 2
- A tap on a card opens the Checkout of that book (`checkout/{bookId}`).
- State down: `state`. Events up: `onBookClick`, `onFavoriteClick`.
- After rotation the hearts stay. The ViewModel survives, so the repository is not asked again.
- The ViewModel has a constructor parameter, so a factory is needed to create it. The repository is supplied by `AppContainer` in :app.

### Task 3
- Without `@get:Rule` the tests fail with `IllegalStateException: Module with the Main dispatcher is missing`.
- The fourth test is `twoDifferentFavoritesAreBothMarked`.
- `FakeBookRepository` lives in :core:testing. A test must never import from :app.

## Part B — MVC and MVP

### Task 4
- The filter `books.filter { it.id in favoriteIds }` lives in the View (the composable). It cannot be unit-tested without Compose, because it sits inside a composable function.
- MVC does not have this MVP rule: "the View cannot access the Model".

### Task 5
- (1) The View cannot access the Model: `FavoritesContract.View` has only `show...` methods and no repository. (2) The Presenter is tied to a single View: `private var view: FavoritesContract.View?`. (3) The View is passive: it only implements `showLoading`, `showFavorites` and `showEmpty`.
- Without `job?.cancel()` the collection would keep running after the user leaves, which wastes work and leaks the old View.
- The filter is now in a plain Kotlin class with no Android code, so it can be tested on the JVM.

### Task 6
- After removing a favorite and going back to the Catalog, the heart is cleared, because both screens observe the same repository.
- "presenter attach" is logged on every rotation; "catalog init" is logged only once. The Presenter and the view state live in the composition (`remember`) and are recreated on rotation. The ViewModel lives in the ViewModelStore and survives it.
- `rememberCoroutineScope()` plays the role of `viewModelScope`. The composition cancels the work when the screen leaves.
- MVP gave a testable Presenter and a passive View. The cost is more code (contract, view state, attach/detach), and the Presenter does not survive rotation.

### Task 7
- "empty" comes before "favorites:t1" because the Presenter emits immediately on attach, while there are no favorites yet.
- Neither `FavoritesPresenter.kt` nor `FavoritesContract.kt` imports `androidx`.
- The fourth test is `secondViewStartsFreshAndFirstViewGetsNothingNew`.

## Part C — MVI

### Task 8

| Situation | isLoading | book | email | isPlacingOrder | orderNumber | errorMessage |
|---|---|---|---|---|---|---|
| Loading the book | true | null | "" | false | null | null |
| Book loaded, e-mail empty | false | Book | "" | false | null | null |
| Placing the order | false | Book | "a@b.uz" | true | null | null |
| Order done | false | Book | "a@b.uz" | false | "BN-1001" | null |
| Order failed | false | Book | "abc" | false | null | "message" |

- A stored `canSubmit` could get out of sync with the other fields. A computed one is always correct.
- Two nonsense combinations: `isLoading = true` with `orderNumber != null`, and `isPlacingOrder = true` with `book == null`. A sealed interface (Loading / Editing / Placing / Done) makes them impossible, but it costs more types and more code to copy between states.
- From the user: `EmailChanged`, `PlaceOrderClicked`, `ErrorDismissed`. From the app: `BookLoaded`, `OrderPlaced`, `OrderFailed`.

### Task 9
- The ninth test passes without changing `reduce`, because `orderNumber != null` makes `canSubmit` false.
- If `!isPlacingOrder` is removed, `placeOrderStartsTheSpinner` fails.
- `data class` and `copy` never change the original object; `reduce` always returns a new state.

### Task 10
- Intents for a successful order: `BookLoaded` → `EmailChanged` → `PlaceOrderClicked` → `OrderPlaced`.
- A double tap is stopped by `canSubmit` (the `!isPlacingOrder` check in the reducer) and by the check in `onIntent` (`!previous.isPlacingOrder && next.isPlacingOrder`).
- Navigation puts `bookId` into `SavedStateHandle`. The route is defined in `BookNestNavHost.kt` (`checkout/{bookId}`).
- The test does not wait 600 ms because it uses `StubOrderRepository`, which has no delay.

### Task 11
- The message is cleared by `EmailChanged`.
- Every action is an intent except **Done**, which calls `onFinished`. Done is navigation, not a change of the screen's state.
- Every `if`/`when` in `CheckoutScreen` reads only `state`.
- The two previews are in `CheckoutPreview.kt` (failed and finished).

### Task 12
- Logcat shows `intent = ...` and `state = ...` lines, one for each step.
- Without the fix, all `CheckoutViewModelTest` tests fail with `RuntimeException: Method d in android.util.Log not mocked`.
- `isReturnDefaultValues` makes every Android stub return 0 or null. It hides real errors, so it is a poor fix.
- Behind the real fix: Lecture 10 slide 3, testability (depend on an abstraction), and Lecture 6, dependency injection.

### Task 13
- Without the fix the e-mail was lost after process death.
- I would not save `book` (it is reloaded from the repository) or `isPlacingOrder` / `isLoading` (temporary states that are not valid after a restart).
