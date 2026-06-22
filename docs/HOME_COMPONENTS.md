# Home Components — Card & Section của màn hình Home

Các component dựng nên màn Home (`ui/components/`), lắp ráp trong `ui/screens/home/HomeScreen.kt`. Mỗi "shelf" (kệ) tự quản lề ngang của nó nên xếp thẳng trong một `LazyColumn` là chạy mép-tới-mép.

Xem `COMPONENTS.md` cho component nền tảng, `THEME.md` cho token.

> **Slot ảnh bìa:** chưa tích hợp thư viện ảnh. Các card có tham số `cover: @Composable BoxScope.() -> Unit` với placeholder mặc định. Khi thêm Coil, truyền `AsyncImage(Modifier.fillMaxSize())` vào slot này — **không phải sửa component**.

Cấu trúc 1 section = `SectionHeader` + carousel/list các card:

| Section | Card dùng | Bố cục |
|---|---|---|
| `FeaturedCarousel` | `FeaturedComicCard` | LazyRow, card to "hero" |
| `NewReleasesSection` | `NewReleaseCard` | header + LazyRow |
| `PopularGenresSection` | `GenreCard` | dải nền đen + header + LazyRow |
| `TopRatedSection` | `RankedEpicCard` | header + list dọc |

---

## 1. `FeaturedComicCard` + `FeaturedCarousel`

### `FeaturedComicCard`
Thẻ "hero" cao, chữ nhật đứng: ảnh bìa landscape ở trên + footer đen (title trắng Anton + mô tả). Bóng **cam** đánh dấu hero. Badge "FEATURED STORY".

```kotlin
@Composable
fun FeaturedComicCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    coverHeight: Dp = 260.dp,
    shadowColor: Color = MaterialTheme.colorScheme.primary,
    cover: @Composable BoxScope.() -> Unit = { /* placeholder */ },
)
```

### `FeaturedCarousel`
LazyRow các `FeaturedComicCard`, mỗi card rộng cố định `cardWidth` (mặc định 300dp) để card kế ló ra.

```kotlin
@Composable
fun FeaturedCarousel(
    comics: List<ComicEntity>,
    onComicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 300.dp,
)
```

```kotlin
FeaturedCarousel(comics = sections.featured, onComicClick = { id -> openComic(id) })
```

---

## 2. `NewReleaseCard` + `NewReleasesSection`

### `NewReleaseCard`
**Hai hình chữ nhật rời**: ảnh (có viền + bóng) ở trên, một khoảng hở, rồi khung chữ (viền, **không bóng**) chứa title / tác giả / rating.

```kotlin
@Composable
fun NewReleaseCard(
    title: String,
    author: String,
    rating: String,           // ví dụ "4.5"
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    coverHeight: Dp = 160.dp,
    cover: @Composable BoxScope.() -> Unit = { /* placeholder */ },
)
```

### `NewReleasesSection`
Header "NEW RELEASES" + "VIEW ALL →" trên một LazyRow card.

```kotlin
@Composable
fun NewReleasesSection(
    comics: List<ComicEntity>,
    onComicClick: (Int) -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "New Releases",   // đổi để tái dùng cho kệ khác cùng kiểu
    cardWidth: Dp = 150.dp,
)
```

```kotlin
NewReleasesSection(
    comics = sections.newReleases,
    onComicClick = { openComic(it) },
    onViewAll = { openAllNewReleases() },
)
```

---

## 3. `PopularGenresSection` (dùng `GenreCard`)

Dải nền **đen full-bleed**, header chữ trắng, LazyRow các `GenreCard` **tự xoay vòng màu** (cam/xanh/tertiary) và xen kẽ fill/outline.

```kotlin
@Composable
fun PopularGenresSection(
    genres: List<String>,
    onGenreClick: (String) -> Unit,
    modifier: Modifier = Modifier,
)
```

```kotlin
PopularGenresSection(genres = sections.genres, onGenreClick = { genre -> filterBy(genre) })
```
> Vì section đã có nền đen riêng, đặt nó như một `item {}` đầy đủ chiều rộng trong `LazyColumn` (đừng bọc padding ngang ngoài).

---

## 4. `RankedEpicCard` + `TopRatedSection`

### `RankedEpicCard`
Card ngang bảng xếp hạng: bìa nhỏ + (title màu theo hạng, "tác giả • thể loại", ⭐ 5 sao + "(12k Ratings)") + **badge `#rank`** vuông có bóng. `accent` tô màu title + badge.

```kotlin
@Composable
fun RankedEpicCard(
    rank: Int,
    title: String,
    author: String,
    genre: String,
    rating: String,          // "5.0"
    ratingsCount: Int,       // 12000 -> hiển thị "12k"
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    cover: @Composable BoxScope.() -> Unit = { /* placeholder */ },
)
```
- Số sao tô = `rating` làm tròn (0–5); `ratingsCount ≥ 1000` rút gọn thành "…k".

### `TopRatedSection`
Header "TOP RATED EPICS" + "EXPLORE →" (xanh) trên list dọc các `RankedEpicCard`. Màu hạng: #1 cam, #2 xanh, #3 tertiary rồi lặp.

```kotlin
@Composable
fun TopRatedSection(
    comics: List<ComicEntity>,
    onComicClick: (Int) -> Unit,
    onExplore: () -> Unit,
    modifier: Modifier = Modifier,
)
```
> Là `Column` thường (không phải LazyColumn) để lồng vừa trong scroll của Home như một item.

```kotlin
TopRatedSection(
    comics = sections.topRated,
    onComicClick = { openComic(it) },
    onExplore = { openLeaderboard() },
)
```

---

## 5. Lắp ráp trong `HomeScreen`

`HomeScreen` là composable **stateless**: nhận `uiState` + lambdas, không tự giữ state. `HomeRoute` mới là chỗ nối `HomeViewModel`.

```kotlin
LazyColumn(
    contentPadding = PaddingValues(vertical = Dimens.Spacing.Gutter),   // chỉ padding DỌC
    verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SectionSpacing),
) {
    item { ComicSearchField(value = query, onValueChange = onSearchQueryChange,
                            modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin)) }

    when (uiState) {
        is HomeUiState.Loading -> item { /* spinner */ }
        is HomeUiState.Error   -> item { /* message */ }
        is HomeUiState.Content -> {
            val results = uiState.searchResults
            if (results != null) {
                // đang tìm: danh sách kết quả phẳng
                items(results, key = { it.id }) { SearchResultItem(it, …) }
            } else {
                val s = uiState.sections
                item { FeaturedCarousel(s.featured, onComicClick) }
                item { NewReleasesSection(s.newReleases, onComicClick, onViewAll) }
                item { PopularGenresSection(s.genres, onGenreClick) }
                item { TopRatedSection(s.topRated, onComicClick, onExplore) }
            }
        }
    }
}
```

**Vì sao `LazyColumn` chỉ có padding dọc?** Mỗi shelf tự thêm lề ngang `Dimens.Spacing.Margin` (qua `contentPadding` của LazyRow hoặc padding của section). Riêng Popular Genres là dải đen tràn viền — nếu padding ngang ở ngoài sẽ làm hở mép. Phần đặt lề ngang riêng: `ComicSearchField` và `SearchResultItem`.

**Luồng dữ liệu (MVVM):** `HomeViewModel.uiState` (`HomeData`: featured / newReleases / genres / topRated) → `HomeRoute` collect → `HomeScreen` render. Click comic → `onComicClick(id)` → điều hướng `ComicDetailRoute`.
