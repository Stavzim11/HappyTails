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
import com.example.happytails.data.models.Dog
import com.example.happytails.databinding.ItemLayoutBinding


    class DogAdapter(private val callBack: DogAdapter.DogListener) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private val dogs = ArrayList<Dog>()

    fun setDogs(dogs: Collection<Dog>) {
        this.dogs.clear()
        this.dogs.addAll(dogs)
        notifyDataSetChanged()
    }

    interface DogListener {
        fun onDogDetailsClicked(dog: Dog)
        fun onFavoriteClicked(dog: Dog)
    }

    inner class DogViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.detailsButton.setOnClickListener(this)
            binding.favoritesButton.setOnClickListener(this)
        }

        fun bind(dog: Dog, context: Context?) {
            binding.itemTitle.text = dog.name
            binding.itemDescription.text = dog.description


            // ViewPager2 with the ImagePagerAdapter
            val imagePagerAdapter = ImagePagerAdapter(dog.photoUrls)
            binding.itemImagePager.adapter = imagePagerAdapter

            binding.detailsButton.setOnClickListener {
                val bundle = Bundle().apply {
                    //TODO Need to add more details
                    putString("name", dog.name)
                    putString("description", dog.description)
                    putString("photo", dog.photo)
                    putString("moreDetails", dog.moreDetails)

                }
                binding.root.findNavController()
                    .navigate(R.id.action_mainFragment_to_dogDetailsFragment, bundle)
            }

            binding.favoritesButton.setOnClickListener {
                dog.isFavorite = !dog.isFavorite
                val toastMeassage = if (dog.isFavorite) {
                    "${dog.name} added to favorites"
                } else {
                    "${dog.name} removed from favorites"
                }
                Toast.makeText(context, toastMeassage, Toast.LENGTH_SHORT).show()

                //room
                //Update dog in the db on a background thread
//                CoroutineScope(Dispatchers.IO).launch {
//                    viewModel.updateDog(dog)
//                }

            }

        }
        override fun onClick(p0: View?) {
            callBack.onDogDetailsClicked(dogs[adapterPosition])
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DogViewHolder(
        ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


            override fun onBindViewHolder(holder: DogViewHolder, position: Int) =
                holder.bind(dogs[position],null)

        override fun getItemCount() = dogs.size

        fun dogAt(index: Int) = dogs[index]

    }
