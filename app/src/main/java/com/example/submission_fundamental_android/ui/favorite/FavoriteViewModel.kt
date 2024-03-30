package com.example.submission_fundamental_android.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission_fundamental_android.database.Favorite
import com.example.submission_fundamental_android.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)  // tes

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}