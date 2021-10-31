package com.example.ariel.flows.moviesflow.activities

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ariel.R
import com.example.ariel.base.activitie.DemoNavigationActivity
import com.example.ariel.databinding.ActivityMainBinding
import com.example.ariel.flows.imagespicker.activities.ImagePickerActivity
import com.example.ariel.flows.locationsflow.activities.MapActivity
import com.example.ariel.flows.moviesflow.fragments.MainFragment
import com.example.ariel.flows.moviesflow.fragments.MainFragmentDirections
import com.example.ariel.flows.moviesflow.fragments.MoviesFragment
import com.example.ariel.flows.moviesflow.fragments.MoviesFragmentDirections
import com.example.ariel.flows.moviesflow.model.MovieDetailResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DemoNavigationActivity(), MoviesFragment.OnItemMovieClick,
    MainFragment.OnMoviesFlowClick {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun currentNavController(): NavController = findNavController(R.id.navHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return when (currentNavController().currentDestination?.id) {
            R.id.main_fragment -> {
                finish()
                false
            }
            else -> currentNavController().navigateUp()
        }
    }

    override fun onMovieClick(detail: MovieDetailResponse) {
        currentNavController().navigate(MoviesFragmentDirections.actionToDetailMovieFragment(detail))
    }

    override fun goToFlowMovies() {
        currentNavController().navigate(MainFragmentDirections.actionToMoviesFragment())
    }

    override fun goLocationFlow() {
        startActivity(Intent(this, MapActivity::class.java))
    }

    override fun goToImagePickerFlow() {
        startActivity(Intent(this, ImagePickerActivity::class.java))
    }
}