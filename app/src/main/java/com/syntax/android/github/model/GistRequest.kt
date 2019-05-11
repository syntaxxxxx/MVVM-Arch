package com.syntax.android.github.model

class GistRequest(val description: String, val files: Map<String, GistFile>, val public: Boolean = true)
