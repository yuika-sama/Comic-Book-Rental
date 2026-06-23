package com.example.comicbookrental.ui.model

import com.example.comicbookrental.data.entities.ComicEntity

/**
 * Model truyện dành riêng cho tầng UI. Chỉ giữ thứ màn hình cần, và format sẵn các
 * chuỗi hiển thị (vd [priceLabel]) để Composable không phải nối chuỗi / xử lý định dạng.
 */
data class ComicUi(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genre: String,
    val author: String,
    val description: String,
    val rating: String,        // vd "4.9" — chỗ hiển thị tự ghép ★
    val ratingsCount: Int,     // vd 12000 — card tự rút gọn thành "12K"
    val priceLabel: String,    // vd "$2.99" — đã format sẵn
)

/** Chuyển bản ghi lưu trữ [ComicEntity] sang model hiển thị [ComicUi]. */
fun ComicEntity.toUi(): ComicUi = ComicUi(
    id = id,
    title = title,
    coverImageUrl = coverImageUrl,
    genre = genre,
    author = author,
    description = description,
    rating = avgRating,
    ratingsCount = ratingsCount,
    priceLabel = "$$rentalPrice",
)
