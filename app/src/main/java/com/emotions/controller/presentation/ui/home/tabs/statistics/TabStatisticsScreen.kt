package com.emotions.controller.presentation.ui.home.tabs.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabStatisticsBinding
import com.emotions.controller.domain.model.Emotion
import com.emotions.controller.domain.model.EmotionItem
import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabStatisticsFragment : BindingViewModelFragment<FragmentTabStatisticsBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_statistics
    override val viewModel: TabStatisticsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = getEntries()
        val pieDataSet = PieDataSet(list, "")
        val pieData = PieData(pieDataSet)
        binding.pieChart.data = pieData
        pieDataSet.colors = context?.let {
            listOf(
                ContextCompat.getColor(it, R.color.ecstasy),
                ContextCompat.getColor(it, R.color.vigilance),
                ContextCompat.getColor(it, R.color.rage),
                ContextCompat.getColor(it, R.color.loathing),
                ContextCompat.getColor(it, R.color.grief),
                ContextCompat.getColor(it, R.color.amazement),
                ContextCompat.getColor(it, R.color.terror),
                ContextCompat.getColor(it, R.color.admiration)
            )
        }

        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 10f
        pieDataSet.sliceSpace = 5f
    }

    private fun getEntries(): ArrayList<PieEntry> {
        val pieEntries = ArrayList<PieEntry>()
        viewModel.getAll().let { list ->
            if (list.size < 15) {
                viewModel.left.set(15 - list.size)
                viewModel.visibleStatistics.set(false)
            } else {
                viewModel.visibleStatistics.set(true)
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.ECSTASY),
                        getString(R.string.ecstasy)
                    )
                )
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.VIGILANCE),
                        getString(R.string.vigilance)
                    )
                )
                pieEntries.add(PieEntry(getInterest(list, Emotion.RAGE), getString(R.string.rage)))
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.LOATHING),
                        getString(R.string.loathing)
                    )
                )
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.GRIEF),
                        getString(R.string.grief)
                    )
                )
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.AMAZEMENT),
                        getString(R.string.amazement)
                    )
                )
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.TERROR),
                        getString(R.string.terror)
                    )
                )
                pieEntries.add(
                    PieEntry(
                        getInterest(list, Emotion.ADMIRATION),
                        getString(R.string.admiration)
                    )
                )
            }
        }
        return pieEntries
    }

    private fun getInterest(list: ArrayList<EmotionItem>, emotion: Emotion): Float =
        (100 / list.size * list.filter { it.emotion == emotion }.size).toFloat()
}

class TabStatisticsViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val left = ObservableInt()
    val visibleStatistics = ObservableBoolean(false)

    fun getAll(): ArrayList<EmotionItem> {
        preferences.list?.let {
            return it
        } ?: run {
            return arrayListOf()
        }
    }
}