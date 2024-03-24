package com.example.submission_fundamental_android.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_fundamental_android.databinding.FragmentDetailUserBinding
import com.example.submission_fundamental_android.ui.viewModel.FragmentViewModel

class FollowsFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    private val fragmentViewModel: FragmentViewModel by activityViewModels()

    private var position = 0
    private var username: String = ""

    companion object {
        const val ARG_POSITION = "0"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mengatur tampilan  layout
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollows.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollows.addItemDecoration(itemDecoration)

        fragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1) {
            fragmentViewModel.follower.observe(viewLifecycleOwner) {
                val adapter = FollowsAdapter()
                adapter.submitList(it)
                binding.rvFollows.adapter = adapter
            }
        } else {
            fragmentViewModel.following.observe(viewLifecycleOwner) {
                val adapter = FollowsAdapter()
                adapter.submitList(it)
                binding.rvFollows.adapter = adapter
            }
        }

        fragmentViewModel.getFollower(username)
        fragmentViewModel.getFollowing(username)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE
    }
}

