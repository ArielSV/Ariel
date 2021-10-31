package com.example.ariel.flows.moviesflow.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ariel.base.fragment.FragmentView
import com.example.ariel.databinding.MainFragmentBinding

class MainFragment : FragmentView() {

    private val binding by lazy {
        MainFragmentBinding.inflate(layoutInflater)
    }

    private var listener : OnMoviesFlowClick? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnMoviesFlowClick
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            buttonMoviesFlow.setOnClickListener { listener?.goToFlowMovies() }
            buttonLocationFlow.setOnClickListener { listener?.goLocationFlow() }
            buttonImagePickerFlow.setOnClickListener { listener?.goToImagePickerFlow() }
        }
    }

    interface OnMoviesFlowClick {
        fun goToFlowMovies()
        fun goLocationFlow()
        fun goToImagePickerFlow()
    }
}