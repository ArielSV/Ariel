package com.example.ariel.flows.imagespicker.activities

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ariel.R
import com.example.ariel.base.activitie.DemoNavigationActivity
import com.example.ariel.databinding.ImagePickerActivityBinding

class ImagePickerActivity : DemoNavigationActivity() {

    private val binding by lazy {
        ImagePickerActivityBinding.inflate(layoutInflater)
    }

    override fun currentNavController(): NavController = findNavController(R.id.navHostFragment)

    override fun onSupportNavigateUp(): Boolean {
        return when (currentNavController().currentDestination?.id) {
            R.id.image_picker_fragment -> {
                finish()
                false
            }
            else -> currentNavController().navigateUp()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}