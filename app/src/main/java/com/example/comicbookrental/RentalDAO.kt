package com.example.comicbookrental

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {

    //lấy list rental lọc theo ngày thuê
    @Query("""
        SELECT *
        FROM rentals
        ORDER BY rentalDate DESC
    """)
    fun getAllRentals():
            Flow<List<RentalEntity>>

    @Insert
    suspend fun insertRental(
        rental: RentalEntity
    )

    @Delete
    suspend fun deleteRental(
        rental: RentalEntity
    )
}