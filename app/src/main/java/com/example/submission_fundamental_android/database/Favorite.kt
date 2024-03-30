package com.example.submission_fundamental_android.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// class ini untuk membuat tabel database
@Parcelize
@Entity(tableName = "Favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null

) : Parcelable
