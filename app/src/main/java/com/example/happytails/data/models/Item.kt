package com.example.happytails.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(

    @ColumnInfo(name = "title")
    val title: String,

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
    val gender: String?
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

object ItemManager {

    val items: MutableList<Item> = mutableListOf()
    val favorites: MutableList<Item> = mutableListOf()

    fun add(item: Item) {
        items.add(item)
    }


    fun remove(index: Int) {
        items.removeAt(index)
    }

    //Deals with list of favorites
    fun toggleFavorite(item: Item): Boolean {
        return if (item.isFavorite) {
            item.isFavorite = false
            favorites.remove(item)

        } else {
            item.isFavorite = true
            favorites.add(item)
            true
        }
    }
}