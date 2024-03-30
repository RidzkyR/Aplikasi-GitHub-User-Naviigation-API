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

    @ColumnInfo(name = "nick_name")
    var nickName: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = true,

    @ColumnInfo(name = "followersCount")
    var follower: String? = null,

    @ColumnInfo(name = "followingCount")
    var following: String? = null,

    ) : Parcelable
