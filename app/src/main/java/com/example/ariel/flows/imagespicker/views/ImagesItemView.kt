package com.example.ariel.flows.imagespicker.views

import android.net.Uri
import com.example.ariel.R
import com.example.ariel.databinding.ItemImagesBinding
import com.xwray.groupie.databinding.BindableItem

class ImagesItemView(
    private val uri: Uri?,
) : BindableItem<ItemImagesBinding>() {

    private lateinit var binding: ItemImagesBinding

    override fun getLayout() = R.layout.item_images

    override fun bind(binding: ItemImagesBinding, position: Int) {
        uri?.let {
            binding.imageViewPhoto.setImageURI(uri)
        }
        this.binding = binding
    }
}
