package com.example.comicbookrental.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.comicbookrental.data.entities.RentalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {

    @Query("SELECT * FROM rentals ORDER BY rentalDate DESC")
    fun getAllRentals(): Flow<List<RentalEntity>>

    @Insert
    suspend fun insertRental(rental: RentalEntity)

    @Delete
    suspend fun deleteRental(rental: RentalEntity)
}