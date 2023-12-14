package com.adhi.githubuser.ui.main.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhi.githubuser.R
import com.adhi.githubuser.data.local.entity.UserEntity
import com.adhi.githubuser.data.remote.Result
import com.adhi.githubuser.databinding.FragmentDashboardBinding
import com.adhi.githubuser.ui.adapters.UserAdapter
import com.adhi.githubuser.ui.main.MainViewModel
import com.adhi.githubuser.utils.NavControllerHelper.safeNavigate
import com.adhi.githubuser.utils.SnackBarExt.showSnackBar
import com.adhi.githubuser.utils.ViewVisibilityUtil.setGone
import com.adhi.githubuser.utils.ViewVisibilityUtil.setInvisible
import com.adhi.githubuser.utils.ViewVisibilityUtil.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment: Fragment(), UserAdapter.UserClickCallback {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        setHasOptionsMenu(true)
        setUpView()
    }

    private fun setUpView() {
        userAdapter = UserAdapter(this)
        hideContent()
        binding?.apply {
            rvMain.layoutManager = LinearLayoutManager(requireActivity())
            rvMain.setHasFixedSize(true)
            rvMain.adapter = userAdapter
        }
        viewModel.dataUser.observe(viewLifecycleOwner, observer)
    }

    private val observer = Observer<Result<List<UserEntity>>> { result ->
        when(result) {
            is Result.Success -> {
                showContent()
                result.data?.let {
                    userAdapter.submitList(it)
                }
            }
            is Result.Error -> {
                showContent()
                requireActivity().showSnackBar(
                    requireActivity().window.decorView.rootView,
                    result.message)
            }
        }
    }

    private fun showContent() = binding?.apply {
        shimmer.root.setGone()
        rvMain.setVisible()
    }

    private fun hideContent() = binding?.apply {
        shimmer.root.setVisible()
        rvMain.setInvisible()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dashboard_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.search -> {
                searchUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchUser() {
        val toOnSearch =
            com.adhi.githubuser.ui.main.dashboard.DashboardFragmentDirections.actionDashboardToOnSearchFragment()
        safeNavigate(toOnSearch, javaClass.name)
    }

    override fun onItemClicked(user: UserEntity) {
        val toDetail =
            com.adhi.githubuser.ui.main.dashboard.DashboardFragmentDirections.actionDashboardToDetailFragment()
        toDetail.username = user.login
        safeNavigate(toDetail, javaClass.name)
    }
}