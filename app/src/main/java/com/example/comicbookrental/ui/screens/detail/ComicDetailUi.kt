package com.example.comicbookrental.ui.screens.detail

import com.example.comicbookrental.ui.components.ReviewUi
import com.example.comicbookrental.ui.components.SimilarTitleUi

data class RentOptionUi(
    val id: String,
    val title: String,
    val price: String,
    val subtitle: String,
    val ctaText: String,
    val highlighted: Boolean = false,
)

data class ComicDetailUi(
    val title: String,
    val creators: String,
    val publisher: String,
    val genres: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val coverUrl: String,
    val badgeText: String?,
    val synopsis: String,
    val rentOptions: List<RentOptionUi>,
    val bonusNote: String?,
    val reviews: List<ReviewUi>,
    val similarTitles: List<SimilarTitleUi>,
)
