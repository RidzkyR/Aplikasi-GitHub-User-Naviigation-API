package com.example.submission_fundamental_android.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission_fundamental_android.database.Favorite
import com.example.submission_fundamental_android.database.FavoriteDao
import com.example.submission_fundamental_android.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// penghubung antara ViewModel dengan database atau resource data
class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> {
        return mFavoriteDao.getAllFavorite()
    }
    fun getIsFavorite(username: String): Boolean {
        return mFavoriteDao.isFavorite(username)
    }

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}