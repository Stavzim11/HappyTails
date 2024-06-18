package com.example.happytails.repository

import com.example.happytails.data.models.User
import com.example.happytails.utils.Resource

interface AuthRepository {

    suspend fun currentUser() : Resource<User>
    suspend fun login(userName:String, password:String) : Resource<User>
    suspend fun createUser(userName:String,
                           userLoginPass:String,
                           userPhone:String,
                           name:String) : Resource<User>
    fun logout()
}