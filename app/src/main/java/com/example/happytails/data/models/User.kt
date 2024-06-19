package com.example.happytails.data.models

data class User(val name:String="",
                val email:String="",
                val phone:String?="",
                val favorites: List<String> = listOf()
)