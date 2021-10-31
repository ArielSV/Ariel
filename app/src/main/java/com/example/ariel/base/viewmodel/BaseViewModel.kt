package com.example.ariel.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposable = CompositeDisposable()
    protected val showProgress = MutableLiveData<Boolean>()
    protected val showMessageText = MutableLiveData<String>()

    fun getShowProgress(): LiveData<Boolean> = showProgress
    fun getShowErrorMessageText(): LiveData<String> = showMessageText

    override fun onCleared() {
        disposable.clear()
    }
}