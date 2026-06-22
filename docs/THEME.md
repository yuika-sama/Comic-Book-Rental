# Theme — Màu, Typography, Font, Spacing

Hệ thiết kế **"Heroic Kinetic"** (xem `DESIGN.md` ở thư mục gốc cho phần "tại sao"). File này là hướng dẫn **dùng** trong code.

> **Quy tắc vàng:** không bao giờ hardcode màu (`Color(0xFF…)`) hay số đo (`16.dp`) trong UI.
> Luôn lấy từ `MaterialTheme.colorScheme.*`, `MaterialTheme.extendedColors.*`, `MaterialTheme.typography.*`, và `Dimens.*`.

Mọi màn hình phải được bọc trong theme (đã làm sẵn ở `MainActivity`):

```kotlin
ComicBookRentalTheme {
    // toàn bộ UI ở đây
}
```

`ComicBookRentalTheme` (`ui/theme/Theme.kt`) cung cấp 3 thứ: `colorScheme` (Material), `typography`, và `extendedColors` (màu brand không có slot Material).

---

## 1. Màu (`ui/theme/Color.kt`)

### Màu Material — dùng qua `MaterialTheme.colorScheme`

| Vai trò | Token | Giá trị | Dùng cho |
|---|---|---|---|
| **primary** | Action Orange | `#A93100` | Nút chính, trạng thái active, badge #1, nhấn mạnh "hero" |
| onPrimary | | `#FFFFFF` | Chữ/icon nằm trên nền primary |
| **secondary** | Hero Blue | `#0040E0` | Link phụ, filter, badge #2, "EXPLORE" |
| **tertiary** | | `#005DAA` | Accent thứ 3 (xoay vòng genre/rank) |
| **background / surface** | Paper White | `#FCF9F8` | Nền màn hình, nền card |
| onSurface | | `#1C1B1B` | Chữ chính |
| onSurfaceVariant | | `#5C4037` (nâu) | Chữ phụ, placeholder, tác giả |
| surfaceContainerLowest | | `#FFFFFF` | Nền ô search |
| secondaryContainer | | `#2E5BFF` | Nền placeholder ảnh bìa |
| error | | `#BA1A1A` | Thông báo lỗi |

```kotlin
Text("Hello", color = MaterialTheme.colorScheme.primary)
Box(Modifier.background(MaterialTheme.colorScheme.surface))
```

### Màu brand — dùng qua `MaterialTheme.extendedColors`

Định nghĩa trong `ui/theme/ExtendedColors.kt`. Đây là màu không có slot trong Material:

| Token | Giá trị | Dùng cho |
|---|---|---|
| **ink** | `#121212` | **Viền 2pt** và **hard shadow** của mọi surface |
| success | `#16A34A` | Trạng thái thành công |
| warning | `#EAB308` | Cảnh báo (hết hạn…) |
| info | `#0284C7` | Thông tin |
| **rating** | `#FBBF24` | Sao đánh giá ★ |

```kotlin
val ink = MaterialTheme.extendedColors.ink
Text("★ 4.8", color = MaterialTheme.extendedColors.rating)
```

> ⚠️ `extendedColors` chỉ gọi được trong `@Composable`. Lưu vào biến `val ink = MaterialTheme.extendedColors.ink` ở đầu hàm nếu cần dùng nhiều lần.

---

## 2. Typography (`ui/theme/Type.kt`)

Hai họ chữ: **Anton** (đậm, nén — cho tiêu đề, luôn IN HOA) và **Hanken Grotesk** (chữ chức năng).

Lấy qua `MaterialTheme.typography.<slot>`:

| Slot M3 | Font | Size | Weight | Dùng cho |
|---|---|---|---|---|
| `displayLarge` | Anton | 48 | 400 | Hero "DISCOVER" |
| `headlineLarge` | Anton | 32 | 400 | Tiêu đề lớn |
| `headlineMedium` | Anton | 28 | 400 | |
| `headlineSmall` | Anton | 24 | 400 | **Tiêu đề section**, tên card, genre |
| `titleLarge` | Hanken | 20 | 700 | |
| `titleMedium` | Hanken | 16 | 700 | Giá tiền, badge #rank |
| `titleSmall` | Hanken | 14 | 700 | Tên card nhỏ |
| `bodyLarge` | Hanken | 18 | 500 | Phụ đề |
| `bodyMedium` | Hanken | 16 | 400 | Mô tả, nội dung |
| `bodySmall` | Hanken | 14 | 400 | Tác giả, meta |
| `labelLarge` | Hanken | 14 | 700 | +0.04em | nhãn nút, "VIEW ALL", placeholder search |
| `labelMedium` | Hanken | 12 | 700 | +0.04em | badge "FEATURED STORY" |
| `labelSmall` | Hanken | 12 | 500 | caption |

```kotlin
Text("NEON RECKONING", style = MaterialTheme.typography.headlineSmall)
```

**Mẹo:** với tiêu đề Anton, luôn `.uppercase()` text (DESIGN.md yêu cầu Display/Headline in hoa).

**Đổi font cho 1 slot tạm thời** (ví dụ chữ Anton ở size của titleMedium):

```kotlin
Text(
    "#1",
    style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
)
```

---

## 3. Font (`ui/theme/Font.kt`)

| Biến | Resource | Weight có sẵn |
|---|---|---|
| `Anton` | `res/font/anton_regular.ttf` | Normal (400) — chỉ 1 weight |
| `HankenGrotesk` | `res/font/hanken_grotesk_variable.ttf` (variable) | Normal 400 / Medium 500 / Bold 700 |

Bình thường **không import trực tiếp** `Anton`/`HankenGrotesk` — đã được gắn vào các slot typography ở trên. Chỉ import `Anton` khi cần `.copy(fontFamily = Anton)`.

---

## 4. Spacing & kích thước (`ui/theme/Dimens.kt`)

Lưới 4dp. Dùng `Dimens.*`, **không** gõ số dp trực tiếp.

### `Dimens.Spacing`
| Token | dp | Dùng cho |
|---|---|---|
| `Margin` | 20 | Lề ngoài màn hình / contentPadding của LazyRow |
| `Gutter` | 16 | Khoảng cách giữa các card |
| `SectionSpacing` | 24 | Khoảng cách giữa các section |
| `ContentSpacing` | 12 | Padding trong card, khoảng cách nội dung |
| `ListItemSpacing` | 8 | Khoảng cách item nhỏ |
| `ScreenPadding` | 16 | Padding chung |
| `StackSm / StackMd / StackLg` | 8 / 16 / 32 | Stack dọc |

### `Dimens.Radius`
`Sm` 4 · `Default` 8 · `Md` 12 · `Lg` 16 · `Xl` 24 · `Full` 9999 — và bản ngữ nghĩa: `Card` 16, `Button` 12, `TextField` 12, `Inner` 4.
> Các card "Heroic Kinetic" đang dùng **`Radius.Sm` (4dp)** cho dáng vuông vức.

### `Dimens.Border`
`Hairline` 1.5 · **`Standard` 2** (viền mặc định) · `Focused` 3 (input khi focus).

### `Dimens.Elevation` (độ lệch hard shadow — KHÔNG blur)
`None` 0 · **`Resting` 4** (mặc định) · `Raised` 6 (nổi cao hơn, dùng cho hero card).

### `Dimens.Sizes` & `Dimens.Icon`
`ButtonHeight` 48 · `LargeButtonHeight` 56 · `BottomBarHeight` 64 · `AvatarSize` 48.
`Icon.Small` 16 · `Icon.Medium` 24 · `Icon.Large` 32.

```kotlin
Column(
    verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SectionSpacing),
    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
)
```

---

## 5. Hard shadow — `Modifier.comicHardShadow()`

Đặc trưng của hệ design: **bóng đặc, lệch, không blur** (`ui/components/ComicModifiers.kt`).

```kotlin
fun Modifier.comicHardShadow(
    shape: Shape = RoundedCornerShape(Dimens.Radius.Card),
    offset: Dp = Dimens.Elevation.Resting,
    color: Color = InkBlack,
): Modifier
```

**Thứ tự modifier rất quan trọng** (đọc trên→dưới = sau→trước):

```kotlin
val shape = RoundedCornerShape(Dimens.Radius.Sm)
Box(
    Modifier
        .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink) // vẽ ĐẦU TIÊN, nằm dưới
        .clip(shape)            // bo góc nội dung
        .background(color)      // nền
        .border(Dimens.Border.Standard, ink, shape) // viền 2pt
)
```

> ❗ Nếu đặt `.clip()` **trước** `.comicHardShadow()`, bóng sẽ bị cắt mất.
> Dùng `color = primary` để có bóng **cam** đánh dấu "hero" (featured card); mặc định `ink` cho bóng đen.

---

## Checklist khi viết UI mới
- [ ] Bọc trong `ComicBookRentalTheme` (preview cũng vậy).
- [ ] Màu từ `colorScheme` / `extendedColors`, không hardcode.
- [ ] Chữ từ `typography`, tiêu đề Anton thì `.uppercase()`.
- [ ] Spacing/size/radius/border từ `Dimens`.
- [ ] Surface = `comicHardShadow → clip → background → border` (đúng thứ tự).
- [ ] Thêm `@Preview` với `backgroundColor = 0xFFFCF9F8`.
