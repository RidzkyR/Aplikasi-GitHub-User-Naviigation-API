package com.example.submission_fundamental_android.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_fundamental_android.database.Favorite
import com.example.submission_fundamental_android.databinding.AdapterUserBinding
import com.example.submission_fundamental_android.ui.detail.DetailUserActivity

class FavoriteAdapter: ListAdapter<Favorite, FavoriteAdapter. ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    class ViewHolder(private val binding: AdapterUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.root.setOnClickListener {
                Intent(binding.root.context, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, favorite.username)
                    binding.root.context.startActivity(it)
                }
            }

            binding.tvUsername.text = favorite.username
            Glide.with(binding.root.context)
                .load(favorite.avatarUrl)
                .into(binding.ivProfilePict)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}