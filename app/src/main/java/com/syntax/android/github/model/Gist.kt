package com.syntax.android.github.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Gist(@SerializedName("created_at") val createdAt: Date,
                val description: String,
                val files: Map<String, GistFile>,
                val id: String)