package com.emotions.controller.presentation.ui.home.tabs.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabHistoryBinding
import com.emotions.controller.domain.model.EmotionItem
import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabHistoryFragment : BindingViewModelFragment<FragmentTabHistoryBinding>(),
    OnEmotionItemListItemViewClickListener {

    override val layoutId: Int = R.layout.fragment_tab_history
    override val viewModel: TabHistoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = HistoryAdapter(viewModel.historyList, this)
        viewModel.getList()
    }

    override fun onClick(item: EmotionItem) {
        findNavController().navigate(
            TabHistoryFragmentDirections.actionTabHistoryFragmentToAddEmotionFragment(
                item.emotion.ordinal,
                Gson().toJson(item)
            )
        )
    }
}

class TabHistoryViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val historyList: ArrayList<EmotionItem> = ArrayList()

    fun getList() {
        preferences.list?.let {
            historyList.clear()
            historyList.addAll(it.sortedBy { item -> item.timeInMillis }.reversed())
        }
    }
}