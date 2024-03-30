package com.example.submission_fundamental_android.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_fundamental_android.data.response.ItemsItem
import com.example.submission_fundamental_android.databinding.AdapterUserBinding
import com.example.submission_fundamental_android.ui.detail.DetailUserActivity

class MainAdapter : ListAdapter<ItemsItem, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ViewHolder(private val binding: AdapterUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.root.setOnClickListener {
                Intent(binding.root.context, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
                    binding.root.context.startActivity(it)
                }
            }

            binding.tvUsername.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivProfilePict)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}