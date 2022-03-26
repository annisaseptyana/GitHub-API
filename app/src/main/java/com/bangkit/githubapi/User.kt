package com.bangkit.githubapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String?,
    var url: String?,
    var avatar_url: String?
): Parcelable