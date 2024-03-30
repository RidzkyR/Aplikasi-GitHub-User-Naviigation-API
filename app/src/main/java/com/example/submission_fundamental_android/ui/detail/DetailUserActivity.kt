package com.example.submission_fundamental_android.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission_fundamental_android.R
import com.example.submission_fundamental_android.database.Favorite
import com.example.submission_fundamental_android.databinding.ActivityDetailUserBinding
import com.example.submission_fundamental_android.helper.ViewModelFactory
import com.example.submission_fundamental_android.ui.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_follower,
            R.string.tab_title_following
        )
    }

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menangkap data
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        if (username != null) {
            detailViewModel.userDetail(username)
        }

        //konfigurasi tab layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabsLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.ivDetailProfile)

                binding.apply {
                    tvNickname.text = it.nickName
                    tvDetailUsername.text = it.username
                    tvFollower.text = getString(R.string.tv_follower, it.follower)
                    tvFollowing.text = getString(R.string.tv_following, it.following)

                    fabAdd.contentDescription = it.isFavorite.toString()

                    if (!it.isFavorite) {
                        fabAdd.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailUserActivity, R.drawable.ic_fav_border
                            )
                        )
                    } else {
                        fabAdd.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailUserActivity, R.drawable.ic_fav_solid
                            )
                        )
                    }

                }
            }
        }

        // tes
        binding.apply {
            fabAdd.setOnClickListener {
                val favorite = Favorite(
                    username = tvDetailUsername.text.toString(),
                    avatarUrl = avatar.toString(),
                    isFavorite = true
                )
                detailViewModel.insert(favorite)

                val currentIcon = fabAdd.contentDescription

                if (currentIcon == "true") {
                    fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailUserActivity,
                            R.drawable.ic_fav_border
                        )
                    )
                    detailViewModel.delete(favorite)
                    fabAdd.contentDescription = "false"
                } else {
                    fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailUserActivity,
                            R.drawable.ic_fav_solid
                        )
                    )
                    detailViewModel.insert(favorite)
                    fabAdd.contentDescription = "true"
                }
            }
        }
        // tes
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE
    }
}