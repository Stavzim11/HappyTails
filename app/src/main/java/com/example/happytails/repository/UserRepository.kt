package com.example.happytails.repository

import com.example.happytails.data.models.User
import com.example.happytails.utils.Resource

interface UserRepository {

    suspend fun currentUser(): Resource<User>
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun createUser(
        userName: String,
        userEmail: String,
        userPhone: String,
        userLoginPass: String
    ): Resource<User>

    suspend fun getUserFavs(): List<String>

    suspend fun isConnected(): Boolean
    fun logout()
}