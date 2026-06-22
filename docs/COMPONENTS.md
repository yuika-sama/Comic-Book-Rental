# Components — Thư viện nền tảng

Các component tái dùng trong `ui/components/`. Tất cả đã theo hệ "Heroic Kinetic" (viền ink 2pt + hard shadow + token từ `Dimens`). Xem `THEME.md` cho màu/typography/spacing, và `HOME_COMPONENTS.md` cho card/section riêng của màn Home.

> Tất cả ví dụ giả định đang nằm trong `ComicBookRentalTheme { … }`.

Mục lục:
1. [comicHardShadow](#1-modifiercomichardshadow) — modifier bóng
2. [ComicCard](#2-comiccard) — surface khung nền
3. [ComicButton](#3-comicbutton) — nút
4. [ComicSearchField](#4-comicsearchfield) — ô tìm kiếm
5. [GenreCard](#5-genrecard) — chip thể loại
6. [SectionHeader](#6-sectionheader) — tiêu đề section

---

## 1. `Modifier.comicHardShadow()`

Bóng đặc lệch, nền tảng của mọi surface. Chi tiết + thứ tự modifier xem `THEME.md › Hard shadow`.

```kotlin
Modifier.comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
```

---

## 2. `ComicCard`

Surface khung cơ bản: bo góc + viền ink 2pt + hard shadow. Mọi thứ "dạng thẻ" nên dựng trên nó để viền/bóng/góc đồng nhất. Là một `ColumnScope`.

```kotlin
@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(Dimens.Radius.Card),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.extendedColors.ink,
    borderWidth: Dp = Dimens.Border.Standard,
    shadowOffset: Dp = Dimens.Elevation.Resting,
    contentPadding: PaddingValues = PaddingValues(Dimens.Spacing.ContentSpacing),
    content: @Composable ColumnScope.() -> Unit,
)
```

**Ví dụ:**
```kotlin
ComicCard(modifier = Modifier.fillMaxWidth().clickable { open() }) {
    Text("Tiêu đề", style = MaterialTheme.typography.titleLarge)
    Text("Mô tả", style = MaterialTheme.typography.bodyMedium)
}
```

- Muốn **bóng cam** (hero): `borderColor = MaterialTheme.colorScheme.primary` (lưu ý: bóng dùng chung màu `borderColor`).
- Muốn **không bóng**: `shadowOffset = Dimens.Elevation.None`.

---

## 3. `ComicButton`

Nút brutalist với hiệu ứng nhấn lún vào bóng. 2 biến thể.

```kotlin
@Composable
fun ComicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ComicButtonVariant = ComicButtonVariant.Primary,
    enabled: Boolean = true,
)

enum class ComicButtonVariant { Primary, Secondary }
```

| Variant | Nền | Chữ | Bóng |
|---|---|---|---|
| `Primary` | Action Orange | trắng, Anton IN HOA | có (ink) |
| `Secondary` | Paper White | ink | **không** |

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd)) {
    ComicButton("Rent now", onClick = { rent() })                                  // Primary
    ComicButton("Details", onClick = { details() }, variant = ComicButtonVariant.Secondary)
}
```
> Text của Primary tự `.uppercase()`. `enabled = false` làm mờ 40% và chặn click.

---

## 4. `ComicSearchField`

Ô tìm kiếm 1 dòng: viền dày lên 2pt→3pt khi focus, icon kính lúp, placeholder IN HOA đậm.

```kotlin
@Composable
fun ComicSearchField(
    shadowOffset: Dp = Dimens.Elevation.Resting,
    borderColor: Color = MaterialTheme.extendedColors.ink,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search for heroes, villains, or epics",
)
```

> ⚠️ `value`/`onValueChange` nằm sau hai tham số có default → **luôn gọi bằng tên tham số**.

```kotlin
var query by rememberSaveable { mutableStateOf("") }
ComicSearchField(
    value = query,
    onValueChange = { query = it },
    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
)
```
- Placeholder tự `.uppercase()` và cắt 1 dòng bằng "…".
- Bàn phím dùng `ImeAction.Search`.

---

## 5. `GenreCard`

Chip thể loại hình chữ nhật, viền dày, chữ Anton, có hard shadow. Hai kiểu qua `filled`.

```kotlin
@Composable
fun GenreCard(
    label: String,
    modifier: Modifier = Modifier,
    accent: Color = MaterialTheme.colorScheme.primary,
    filled: Boolean = true,
    onClick: () -> Unit = {},
)
```

| `filled` | Nền | Chữ | Viền + bóng |
|---|---|---|---|
| `true` | `accent` | trắng | ink |
| `false` | Paper | `accent` | `accent` |

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd)) {
    GenreCard("Action", accent = MaterialTheme.colorScheme.primary, filled = true)
    GenreCard("Sci-Fi", accent = MaterialTheme.colorScheme.secondary, filled = false)
}
```
> Thường không dùng lẻ — xem `PopularGenresSection` (tự xoay vòng màu & fill/outline).

---

## 6. `SectionHeader`

Tiêu đề section: chữ Anton IN HOA + gạch chân màu, kèm link "VIEW ALL →" / "EXPLORE →" tùy chọn.

```kotlin
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,           // null => ẩn link
    onActionClick: (() -> Unit)? = null,   // null => ẩn link
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    underlineColor: Color = MaterialTheme.colorScheme.primary,
    actionColor: Color = MaterialTheme.colorScheme.primary,
)
```

Link chỉ hiện khi có **cả** `actionLabel` và `onActionClick`. Title & actionLabel tự `.uppercase()`.

```kotlin
// Có link cam (New Releases)
SectionHeader("New Releases", actionLabel = "View All", onActionClick = { … })

// Link xanh (Top Rated)
SectionHeader(
    "Top Rated Epics",
    actionLabel = "Explore",
    onActionClick = { … },
    actionColor = MaterialTheme.colorScheme.secondary,
)

// Trên nền tối, không link (Popular Genres)
SectionHeader("Popular Genres", titleColor = MaterialTheme.colorScheme.surface)
```

---

## Quy ước khi thêm component mới
- File 1 component (+ private helper) trong `ui/components/`, kèm KDoc và `@Preview`.
- Slot ảnh: nhận `cover: @Composable BoxScope.() -> Unit` (chưa có thư viện ảnh — dùng placeholder mặc định, ghép Coil sau).
- Tham số màu để tái dùng (accent/titleColor…) thay vì cố định.
- Dựng surface theo thứ tự `comicHardShadow → clip → background → border`.
