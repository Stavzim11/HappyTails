package com.example.happytails.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.databinding.ItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAdapter(val items: List<Item>, val navAction: (Bundle) -> Unit, val viewModel : MainFragmentViewModel) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Item,
            context: Context,
            navAction: (Bundle) -> Unit,
            viewModel: MainFragmentViewModel
        ) {
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
                    putString("moreDetails", item.moreDetails)
                }
                navAction(bundle)

                //binding.root.findNavController()
                //  .navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }



            binding.favoritesButton.setOnClickListener {
                item.isFavorite = !item.isFavorite
                val toastMeassage = if (item.isFavorite) {
                    "${item.title} added to favorites"
                } else {
                    "${item.title} removed from favorites"
                }
                Toast.makeText(context, toastMeassage, Toast.LENGTH_SHORT).show()

                //Update item in the db on a background thread
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.updateItem(item)
                }

//            binding.favoritesButton.setOnClickListener {
//                if (ItemManager.toggleFavorite(item)) {
//                    Toast.makeText(context, "${item.title} added to favorites", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(
//                        context,
//                        "${item.title} is already in favorites",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], holder.itemView.context, navAction, viewModel)
    }

    override fun getItemCount() = items.size

    fun itemAt(index: Int) = items[index]
}