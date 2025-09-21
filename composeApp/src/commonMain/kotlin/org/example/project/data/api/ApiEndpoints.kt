package org.example.project.data.api

object ApiEndpoints {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    const val USERS = "$BASE_URL/users"
    const val POSTS = "$BASE_URL/posts"

    fun userById(id: Int) = "$USERS/$id"
    fun postById(id: Int) = "$POSTS/$id"
    fun postsByUser(userId: Int) = "$POSTS?userId=$userId"
}