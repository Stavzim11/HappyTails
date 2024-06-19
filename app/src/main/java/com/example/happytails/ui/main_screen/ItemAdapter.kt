package com.example.happytails.ui.main_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.R
import com.example.happytails.data.models.Item
import com.example.happytails.databinding.ItemLayoutBinding
import com.example.happytails.utils.Resource


class ItemAdapter(private val dogss: Resource<List<Item>>, private val callBack: ItemAdapter.ItemListener) : RecyclerView.Adapter<ItemAdapter.itemViewHolder>() {

    private val items = ArrayList<Item>()

    fun setItems(items: Collection<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface ItemListener {
        fun onItemDetailsClicked(item: Item)
        fun onFavoriteClicked(item: Item)
    }

    inner class itemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.detailsButton.setOnClickListener(this)
            binding.favoritesButton.setOnClickListener(this)
        }

        fun bind(item: Item, context: Context?) {
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
                binding.root.findNavController()
                    .navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }

            binding.favoritesButton.setOnClickListener {
                item.isFavorite = !item.isFavorite
                val toastMeassage = if (item.isFavorite) {
                    "${item.title} added to favorites"
                } else {
                    "${item.title} removed from favorites"
                }
                Toast.makeText(context, toastMeassage, Toast.LENGTH_SHORT).show()

                //room
                //Update item in the db on a background thread
//                CoroutineScope(Dispatchers.IO).launch {
//                    viewModel.updateItem(item)
//                }

            }

        }
        override fun onClick(p0: View?) {
            callBack.onItemDetailsClicked(items[adapterPosition])
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = itemViewHolder(
        ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


            override fun onBindViewHolder(holder: itemViewHolder, position: Int) =
                holder.bind(items[position],null)

        override fun getItemCount() = items.size

        fun itemAt(index: Int) = items[index]

    }
