package com.example.ariel.flows.moviesflow.views

import com.example.ariel.R
import com.example.ariel.databinding.ItemMoviesBinding
import com.example.ariel.flows.moviesflow.model.MovieDetailResponse
import com.example.ariel.utils.isNotNullOrBlank
import com.example.ariel.utils.loadImages
import com.xwray.groupie.databinding.BindableItem

class MoviesItemView(
    private val movie: MovieDetailResponse?,
    private val listener: (movie: MovieDetailResponse?) -> Unit,
    private val baseImageUrl: String,
) : BindableItem<ItemMoviesBinding>() {

    private lateinit var binding: ItemMoviesBinding

    override fun getLayout() = R.layout.item_movies

    override fun bind(binding: ItemMoviesBinding, position: Int) {
        binding.apply {
            textViewTitle.text = movie?.originalTitle.orEmpty()
            root.setOnClickListener {
                listener.invoke(movie)
            }
            val urlImage = getImageUrl(movie?.backdropPath.orEmpty())
            if (urlImage.isNotEmpty()) {
                loadImages(binding.root.context, urlImage, imageViewMovieBackground)
            }
        }
        this.binding = binding
    }

    private fun getImageUrl(imageUrl: String): String = if (baseImageUrl.isNotNullOrBlank()) {
        baseImageUrl + imageUrl
    } else {
        ""
    }
}
