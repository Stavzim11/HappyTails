package com.example.happytails.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "moreDetails")
    val moreDetails: String,

    @ColumnInfo(name = "photo")
    val photo: String?,

    @ColumnInfo(name = "photoUrls")
    val photoUrls: List<String>,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "Location")
    val location: String?,

    @ColumnInfo(name = "Age")
    val age: String?,

    @ColumnInfo(name = "Size")
    val size: String?,

    @ColumnInfo(name = "Gender")
    val gender: String?,

    @PrimaryKey var id: Int =0
)

object DogIdGenerator {
    private var currentId: Int = 0

    fun generateId(): Int {
        return currentId++ }
}

//object ItemManager {
//
//    val dogs: MutableList<Dog> = mutableListOf()
//    val favorites: MutableList<Dog> = mutableListOf()
//
//    fun add(dog: Dog) {
//        dogs.add(dog)
//    }
//
//
//    fun remove(index: Int) {
//        dogs.removeAt(index)
//    }
//
//    //Deals with list of favorites
//    fun toggleFavorite(dog: Dog): Boolean {
//        return if (dog.isFavorite) {
//            dog.isFavorite = false
//            favorites.remove(dog)
//
//        } else {
//            dog.isFavorite = true
//            favorites.add(dog)
//            true
//        }
//    }
//}