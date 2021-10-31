package com.example.ariel.flows.moviesflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ariel.base.viewmodel.BaseViewModel
import com.example.ariel.flows.moviesflow.actions.MoviesAction
import com.example.ariel.flows.moviesflow.repositorie.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val demoRepository: DemoRepository
) : BaseViewModel() {

    private val action = MutableLiveData<MoviesAction>()

    fun getAction(): LiveData<MoviesAction> = action

    fun getMovies() {
        disposable.add(
            demoRepository.getMovies()
                .doOnSubscribe {
                    showProgress.value = true
                }
                .doFinally {
                    showProgress.value = false
                }
                .subscribe(
                    {
                        it.results?.let { items ->
                            action.value = MoviesAction.GetMovies(items)
                        } ?: run {
                            showMessageText.value = ""
                        }
                    },
                    {
                        showMessageText.value = it.message.orEmpty()
                        action.value = MoviesAction.GetDataFromDB
                    }
                )
        )
    }
}