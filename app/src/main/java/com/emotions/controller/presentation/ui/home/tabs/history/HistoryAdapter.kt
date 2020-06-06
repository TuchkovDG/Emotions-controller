package com.emotions.controller.presentation.ui.home.tabs.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.emotions.controller.R
import com.emotions.controller.databinding.HistoryItemBinding
import com.emotions.controller.domain.model.EmotionItem

class HistoryAdapter(
    private val items: ArrayList<EmotionItem>,
    private val onClickListener: OnEmotionItemListItemViewClickListener
) : RecyclerView.Adapter<ViewHolderHistory>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory =
        ViewHolderHistory(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.history_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) =
        holder.bind(items[position], onClickListener)
}

class ViewHolderHistory(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: EmotionItem, onClickListener: OnEmotionItemListItemViewClickListener) {
        binding.item = item
        binding.llHistoryItem.setOnClickListener {
            onClickListener.onClick(item)
        }
        binding.executePendingBindings()
    }
}

interface OnEmotionItemListItemViewClickListener {

    fun onClick(item: EmotionItem)
}