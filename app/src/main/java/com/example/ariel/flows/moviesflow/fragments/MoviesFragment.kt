package com.example.ariel.flows.moviesflow.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ariel.R
import com.example.ariel.base.fragment.FragmentView
import com.example.ariel.data.viewmodel.MoviesRoomViewModel
import com.example.ariel.databinding.MoviesFragmentBinding
import com.example.ariel.flows.moviesflow.actions.MoviesAction
import com.example.ariel.flows.moviesflow.model.MovieDetailResponse
import com.example.ariel.flows.moviesflow.model.MoviesRoomDB
import com.example.ariel.flows.moviesflow.viewmodel.DemoViewModel
import com.example.ariel.flows.moviesflow.views.MoviesItemView
import com.example.ariel.utils.orZero
import com.example.ariel.utils.verifyAvailableNetwork
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MoviesFragment : FragmentView() {

    private val binding by lazy {
        MoviesFragmentBinding.inflate(layoutInflater)
    }

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private var listener: OnItemMovieClick? = null

    private val viewModel: DemoViewModel by activityViewModels()

    private val viewModelRoom: MoviesRoomViewModel by activityViewModels()

    private var dbIsEmpty = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnItemMovieClick
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initRecyclerView()
    }

    private fun bindViewModel() {
        viewModel.getShowProgress().observe(viewLifecycleOwner, Observer(this::showLoading))
        viewModel.getShowErrorMessageText().observe(viewLifecycleOwner, Observer(this::showError))
        viewModel.getAction().observe(viewLifecycleOwner, Observer(this::handleAction))
        viewModelRoom.readAllMovies.observe(viewLifecycleOwner, { movie ->
            dbIsEmpty = movie.isNullOrEmpty()
            movie?.let {
                if (it.isNotEmpty() && verifyAvailableNetwork(requireActivity()).not()) {
                    val moviesList = mutableListOf<MovieDetailResponse>()
                    movie.map { movies ->
                        val moviesDB = MovieDetailResponse(
                            adult = false,
                            id = movies.id.orZero(),
                            backdropPath = movies.backdropPath,
                            originalLanguage = movies.originalLanguage,
                            originalTitle = movies.originalTitle,
                            overview = movies.overview,
                            popularity = movies.popularity,
                            posterPath = "",
                            releaseDate = movies.releaseDate,
                            voteAverage = movies.voteAverage,
                            voteCount = movies.voteCount
                        )
                        moviesList.add(moviesDB)
                    }
                    showMovies(moviesList)
                } else if (it.isEmpty()) {
                    showError(getString(R.string.error_db_empty))
                }
            }
        })
        Handler().postDelayed({
            if (verifyAvailableNetwork(requireActivity())) {
                viewModel.getMovies()
            } else if (dbIsEmpty) {
                showError(getString(R.string.internet_error_message))
            }
        }, 500)
    }

    private fun initRecyclerView() {
        binding.recyclerViewMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = groupAdapter
        }
    }

    private fun handleAction(action: MoviesAction) {
        if (action is MoviesAction.GetMovies) {
            //Esto lo deberiamos de guardar en el repository, pero quería experimentar con room
            action.movies.map {
                val movie = MoviesRoomDB(
                    id = it.id.orZero(),
                    backdropPath = it.backdropPath,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                )
                viewModelRoom.addMovie(movie)
            }
            showMovies(action.movies)
        }
    }

    private fun showMovies(movies: List<MovieDetailResponse>) {
        groupAdapter.clear()
        groupAdapter.addAll(
            movies.map {
                createItem(it)
            }
        )
    }

    private fun createItem(movieResponse: MovieDetailResponse?): MoviesItemView {
        return MoviesItemView(
            movieResponse, { movie ->
                movie?.let {
                    listener?.onMovieClick(it)
                }
            },
            URL_IMAGE_BASE
        )
    }

    interface OnItemMovieClick {
        fun onMovieClick(detail: MovieDetailResponse)
    }

    private companion object {
        const val URL_IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
    }
}