package com.example.submission_fundamental_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_fundamental_android.R
import com.example.submission_fundamental_android.data.response.ItemsItem
import com.example.submission_fundamental_android.databinding.ActivityMainBinding
import com.example.submission_fundamental_android.ui.favorite.FavoriteActivity
import com.example.submission_fundamental_android.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // mengatur tampilan  layout
        val layoutManager = LinearLayoutManager(this)
        binding.rvListItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListItem.addItemDecoration(itemDecoration)

        // Menampilkan listUser
        mainViewModel.listUser.observe(this) {
            if (it.isNullOrEmpty()) {
                userNotFound(true)
            } else {
                userNotFound(false)
            }
            setUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //SearchView
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.menu)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchUser(searchBar.text.toString())
                    false
                }

            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.fav_menu -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

        }
    }

    private fun searchUser(username: String) {
        if (username.isEmpty()) {
            Toast.makeText(this@MainActivity, "Tolong Masukan Username", Toast.LENGTH_SHORT).show()
            return
        }
        showLoading(true)
        mainViewModel.user(username)
    }

    // Koneksi Adapter
    private fun setUserData(user: List<ItemsItem>) {
        val adapter = MainAdapter()
        adapter.submitList(user)
        binding.rvListItem.adapter = adapter
    }

    private fun userNotFound(value: Boolean) {
        binding.apply {
            if (value) {
                rvListItem.visibility = View.GONE
                tvNotFound.visibility = View.VISIBLE
            } else {
                rvListItem.visibility = View.VISIBLE
                tvNotFound.visibility = View.GONE
            }
        }
    }

    // Tampilan Loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE
    }
}