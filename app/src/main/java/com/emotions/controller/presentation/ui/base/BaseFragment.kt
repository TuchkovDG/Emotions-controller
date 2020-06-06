package com.emotions.controller.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.emotions.controller.BR

abstract class BindingViewModelFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B

    abstract val layoutId: Int
    abstract val viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
        removeObserver(observer)
        observe(owner, observer)
    }
}