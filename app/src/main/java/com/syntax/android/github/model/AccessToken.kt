package com.syntax.android.github.model

import com.google.gson.annotations.SerializedName


class AccessToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String)