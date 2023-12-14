package com.adhi.githubuser.data.remote.response.search

import com.google.gson.annotations.SerializedName
import com.adhi.githubuser.data.remote.response.user.UserResponse

data class SearchResponse(

	@field:SerializedName("items")
	val items: List<UserResponse>
)
