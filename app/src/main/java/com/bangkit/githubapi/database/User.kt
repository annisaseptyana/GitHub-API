package com.bangkit.githubapi.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String?,
    var html_url: String?,
    var avatar_url: String?
): Parcelable