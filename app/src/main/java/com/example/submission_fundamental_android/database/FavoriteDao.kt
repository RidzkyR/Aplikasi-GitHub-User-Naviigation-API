package com.example.submission_fundamental_android.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

//  class ini untuk melakukan eksekusi quiring
@Dao
interface FavoriteDao {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insert(favorite: Favorite)

        @Delete
        fun delete(favorite: Favorite)

        @Query("SELECT * from favorite ORDER BY username ASC")
        fun getAllFavorite(): LiveData<List<Favorite>>

        @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE username= :username AND isFavorite = 1)")
        fun isFavorite(username: String): Boolean
}