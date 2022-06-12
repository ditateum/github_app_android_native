package com.ditateum.githubappsubmission2.view.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ditateum.githubappsubmission2.R
import com.ditateum.githubappsubmission2.databinding.FragmentGithubFollowBinding
import com.ditateum.githubappsubmission2.view.adapter.UserAdapter

class FollowersFragment : Fragment(R.layout.fragment_github_follow) {
    private var _binding : FragmentGithubFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FollowersViewModel
    private lateinit var followersAdapter : UserAdapter
    private lateinit var username : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        followersAdapter = UserAdapter()
        followersAdapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = followersAdapter
        }

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                followersAdapter.setList(it)
                showProgressBar(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}