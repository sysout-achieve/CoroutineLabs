package com.gunt.coroutinelabs.data.remote.model

import com.google.gson.annotations.SerializedName

data class ExampleResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)
