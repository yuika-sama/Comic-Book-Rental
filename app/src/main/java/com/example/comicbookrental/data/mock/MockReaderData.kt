package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.entities.ReaderPageEntity


object MockReaderData {

    private val pagesByComicId: Map<Int, List<ReaderPageEntity>> = mapOf(


        1 to listOf(
            ReaderPageEntity(
                pageNumber = 1,
                imageUrl = "https://picsum.photos/seed/solo-leveling-page-1/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 2,
                imageUrl = "https://picsum.photos/seed/solo-leveling-page-2/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 3,
                imageUrl = "https://picsum.photos/seed/solo-leveling-page-3/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 4,
                imageUrl = "https://picsum.photos/seed/solo-leveling-page-4/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 5,
                imageUrl = "https://picsum.photos/seed/solo-leveling-page-5/1080/1600"
            )
        ),

        // Comic có comicId = 2: One Piece
        2 to listOf(
            ReaderPageEntity(
                pageNumber = 1,
                imageUrl = "https://picsum.photos/seed/one-piece-page-1/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 2,
                imageUrl = "https://picsum.photos/seed/one-piece-page-2/1080/1600"
            ),
            ReaderPageEntity(
                pageNumber = 3,
                imageUrl = "https://picsum.photos/seed/one-piece-page-3/1080/1600"
            )
        ),

        // Comic có comicId = 3: Chainsaw Man
        3 to listOf(
            ReaderPageEntity(
                pageNumber = 1,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/zhrPxW7qE3gwdPXzXOheQZ7aDyfrGR1751550343.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 2,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/GnhgUkuSJv6FlFns0cnwA9xQJrnYgi1751550345.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 3,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/O2ROf6KBasECVC1ElyOZ1ETLtW2ZJG1751550346.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 4,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/gOaYphqpwSJD3agLZZyKQD9FmAJ9S01751550347.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 5,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/WiyqxCJTVBnBxQVrXq4gKlS06R7lCO1751550347.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 6,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/nhESMGSP10YfYr7a935A2IX9xqYk6n1751550348.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 7,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/Lwmta5LAjl8VYobNbgiHzMUqySZHoN1751550349.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 8,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/RK1LYMCxVgSjpj0AsuLlava6eDghhz1751550350.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 9,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/ODGoVG0sdKf1MdWYzvdAbkLV77EhTL1751550351.jpg"
            ),
            ReaderPageEntity(
                pageNumber = 10,
                imageUrl = "https://laiond.com/images/Chainsaw%20Man/2VGx5Dtl1DLhSeCcJzeIAKPBpFeP431751550352.jpg"
            ),


        )
    )

    fun getPagesForComic(comicId: Int): List<ReaderPageEntity> {
        return pagesByComicId[comicId].orEmpty()
    }
}