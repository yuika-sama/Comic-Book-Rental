package com.example.comicbookrental.data.repositories.comic

import com.example.comicbookrental.data.entities.ComicEntity

/**
 * Hard-coded sample catalog used to seed the DB during development so the Home screen has
 * something to render before a real backend exists. Remove once data comes from the network.
 *
 * A mix of genres (Action / Sci-Fi / Noir / Fantasy / Mystery) plus a few `isFeatured` entries
 * with high `viewCount` so every Home section has content.
 */
object SampleComics {
    val list = listOf(
        ComicEntity(
            id = 1,
            title = "Neon Reckoning: Issue #1",
            coverImageUrl = "",
            genre = "Action",
            author = "R. Vance",
            publisher = "Panel Rush",
            description = "In a world of metal and madness, one hero rises to reclaim the grid. " +
                "Experience the dawn of a new legend.",
            avgRating = "4.9",
            rentalPrice = "3.49",
            releaseDate = "2025-05-20",
            viewCount = 48200,
            ratingsCount = 15300,
            isFeatured = true,
        ),
        ComicEntity(
            id = 2,
            title = "Cyber Saints",
            coverImageUrl = "",
            genre = "Sci-Fi",
            author = "K. Mori",
            publisher = "Panel Rush",
            description = "When the city's AI gods turn on their makers, a band of outcasts becomes " +
                "humanity's last prayer.",
            avgRating = "4.7",
            rentalPrice = "2.99",
            releaseDate = "2025-04-11",
            viewCount = 39100,
            ratingsCount = 9800,
            isFeatured = true,
        ),
        ComicEntity(
            id = 3,
            title = "The Last Warrior",
            coverImageUrl = "",
            genre = "Fantasy",
            author = "H. Miller",
            publisher = "Dark Horse",
            description = "A lone knight marches across a dying kingdom to face the prophecy that " +
                "claimed everyone he loved.",
            avgRating = "5.0",
            rentalPrice = "3.49",
            releaseDate = "2024-11-02",
            viewCount = 51200,
            ratingsCount = 12000,
            isFeatured = true,
        ),
        ComicEntity(
            id = 4,
            title = "Cobalt Mystery",
            coverImageUrl = "",
            genre = "Noir",
            author = "S. Lane",
            publisher = "Viz Media",
            description = "A rain-soaked detective chases a killer who only strikes under neon blue " +
                "light.",
            avgRating = "4.9",
            rentalPrice = "2.49",
            releaseDate = "2025-01-18",
            viewCount = 27400,
            ratingsCount = 8500,
            isFeatured = false,
        ),
        ComicEntity(
            id = 5,
            title = "Tree of Aeons",
            coverImageUrl = "",
            genre = "Fantasy",
            author = "M. Chen",
            publisher = "Kodansha",
            description = "A wandering seed grows into a world-tree, and with it, the fate of every " +
                "realm it touches.",
            avgRating = "4.9",
            rentalPrice = "2.99",
            releaseDate = "2025-06-01",
            viewCount = 18700,
            ratingsCount = 6400,
            isFeatured = false,
        ),
        ComicEntity(
            id = 6,
            title = "Ghost Protocol",
            coverImageUrl = "",
            genre = "Sci-Fi",
            author = "J. Doe",
            publisher = "Viz Media",
            description = "A deleted agent comes back online with one directive: erase the people " +
                "who erased her.",
            avgRating = "4.2",
            rentalPrice = "2.49",
            releaseDate = "2025-05-28",
            viewCount = 21500,
            ratingsCount = 7100,
            isFeatured = false,
        ),
        ComicEntity(
            id = 7,
            title = "Iron Vendetta",
            coverImageUrl = "",
            genre = "Action",
            author = "D. Castle",
            publisher = "Dark Horse",
            description = "Framed and forgotten, a war hero builds a one-man army to bring down the " +
                "syndicate that buried him.",
            avgRating = "4.6",
            rentalPrice = "2.99",
            releaseDate = "2025-03-09",
            viewCount = 30200,
            ratingsCount = 8900,
            isFeatured = false,
        ),
        ComicEntity(
            id = 8,
            title = "Midnight Ledger",
            coverImageUrl = "",
            genre = "Mystery",
            author = "A. Frost",
            publisher = "Yen Press",
            description = "Every name in the ledger is a debt — and every debt is paid in blood by " +
                "dawn.",
            avgRating = "4.5",
            rentalPrice = "1.99",
            releaseDate = "2025-02-14",
            viewCount = 16400,
            ratingsCount = 5200,
            isFeatured = false,
        ),
    )
}
