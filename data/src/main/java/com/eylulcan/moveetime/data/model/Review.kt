package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Review {
    @SerializedName("author")
    @Expose
    val author: String? = null

    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetails? = null

    @SerializedName("content")
    @Expose
    val content: String? = null

    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String? = null

    @SerializedName("url")
    @Expose
    val url: String? = null
}