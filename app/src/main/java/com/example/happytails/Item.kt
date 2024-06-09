package com.example.happytails

data class Item(
    val title: String,
    val description: String,
    val photo: String?,
    val photoUrls: List<String>
)

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
        return if (favorites.contains(item)) {
            false
        } else {
            favorites.add(item)
            true
        }
    }
}