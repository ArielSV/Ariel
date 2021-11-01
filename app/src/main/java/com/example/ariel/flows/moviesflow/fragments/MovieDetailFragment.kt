package com.example.ariel.flows.moviesflow.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ariel.base.fragment.FragmentView
import com.example.ariel.databinding.MovieDetailFragmentBinding
import com.example.ariel.utils.isNotNullOrBlank
import com.example.ariel.utils.loadImages

class MovieDetailFragment : FragmentView() {

    private val args: MovieDetailFragmentArgs by navArgs()

    private val binding by lazy {
        MovieDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            val urlImage = getImageUrl(args.dataModel?.backdropPath.orEmpty())
            loadImages(
                requireContext(),
                getImageUrl(urlImage),
                imageViewMovieBackground
            )
            textViewMovieTitle.text = args.dataModel?.originalTitle.orEmpty()
            textViewLanguage.append(args.dataModel?.originalLanguage.orEmpty())
            args.dataModel?.voteAverage?.let {
                textViewRating.append(it.toString())
            }
            args.dataModel?.voteCount?.let {
                textViewTotalVotes.append(it.toString())
            }
            textViewOverview.text = args.dataModel?.overview.orEmpty()
            buttonClose.setOnClickListener { findNavController().popBackStack() }
            textViewOverview.movementMethod = ScrollingMovementMethod()
        }
    }

    private fun getImageUrl(imageUrl: String): String = if (args.dataModel?.backdropPath.isNotNullOrBlank()) {
        URL_IMAGE_BASE + imageUrl
    } else {
        ""
    }

    private companion object {
        const val URL_IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
    }
}