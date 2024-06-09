package com.example.happytails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.databinding.ItemLayoutBinding

class ItemAdapter(val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            binding.itemTitle.text = item.title
            binding.itemDescription.text = item.description

            // ViewPager2 with the ImagePagerAdapter
            val imagePagerAdapter = ImagePagerAdapter(item.photoUrls)
            binding.itemImagePager.adapter = imagePagerAdapter

            binding.detailsButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("title", item.title)
                    putString("description", item.description)
                    putString("photo", item.photo)
                }
                binding.root.findNavController()
                    .navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }

            binding.favoritesButton.setOnClickListener {
                if(ItemManager.toggleFavorite(item)) {
                    Toast.makeText(context, "${item.title} added to favorites", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    Toast.makeText(context, "${item.title} is already in favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], holder.itemView.context)
    }

    override fun getItemCount() = items.size
}


//package com.example.happytails
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.navigation.findNavController
//import androidx.recyclerview.widget.RecyclerView
//import com.example.happytails.databinding.ItemLayoutBinding
//import android.content.Context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
//
//
//class ItemAdapter(val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
//
//    class ItemViewHolder(private val binding: ItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Item) {
//            binding.itemTitle.text = item.title
//            binding.itemDescription.text = item.description
//
//            //ViewPager2 with the ImagePagerAdapter
//            val imagePagerAdapter = ImagePagerAdapter(item.photoUrls)
//            binding.itemImagePager.adapter = imagePagerAdapter
//
//            binding.detailsButton.setOnClickListener {
//                val bundle = Bundle().apply {
//                    putString("title", item.title)
//                    putString("description", item.description)
//                    putString("photo", item.photo)
//
//                }
//                binding.root.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
//            }
//
//            //Favorites button in Bottom Navigation
//            binding.favoritesButton.setOnClickListener{
//                ItemManager.toggleFavorite(item)
//                Toast.makeText(context, "${item.title} added to favorites", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
//
//    override fun getItemCount() = items.size
//}