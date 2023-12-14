package com.adhi.githubuser.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhi.githubuser.R
import com.adhi.githubuser.data.local.entity.DetailUserEntity
import com.adhi.githubuser.databinding.FragmentFavoriteBinding
import com.adhi.githubuser.ui.adapters.FavoriteAdapter
import com.adhi.githubuser.ui.main.MainViewModel
import com.adhi.githubuser.utils.NavControllerHelper.safeNavigate
import com.adhi.githubuser.utils.ViewVisibilityUtil.setGone
import com.adhi.githubuser.utils.ViewVisibilityUtil.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapter.OnUserFavCallback, Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var favAdapter: FavoriteAdapter
    private var isDarkMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favAdapter = FavoriteAdapter(this)
        //initThemeMode()
        val loadingProgressBar = binding?.loadingProgressBar // Ambil ProgressBar

        // Pengamatan perubahan pada LiveData
        viewModel.getFavUsers().observe(viewLifecycleOwner, Observer<List<DetailUserEntity>> { users ->
            // Sembunyikan ProgressBar saat data telah dimuat
            loadingProgressBar?.visibility = View.GONE

            if (users.isEmpty()) {
                binding?.noUsers?.setVisible()
            } else {
                binding?.noUsers?.setGone()
                favAdapter.submitList(users)
            }
        })

        // ... kode lainnya ...

        // Konfigurasi RecyclerView
        binding?.rvFav?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = favAdapter
        }
    }

    // ... kode lainnya ...

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.btn_theme_mode -> {
                viewModel.setThemeMode(!isDarkMode)
                true
            }
            else -> false
        }

    override fun onItemClicked(user: DetailUserEntity) {
        val toDetail =
            com.adhi.githubuser.ui.main.favorite.FavoriteFragmentDirections.actionFavoriteToDetailFragment()
        toDetail.username = user.login
        safeNavigate(toDetail, javaClass.name)
    }

    /**private fun initThemeMode() {
        binding?.apply {
            toolbar.setOnMenuItemClickListener(this@FavoriteFragment)
            viewModel.isDarkModeActive().observe(viewLifecycleOwner) { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    toolbar.menu.getItem(0).setIcon(R.drawable.)
                    this@FavoriteFragment.isDarkMode = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    toolbar.menu.getItem(0).setIcon(R.drawable.ic_baseline_moon)
                    this@FavoriteFragment.isDarkMode = false
                }
            }
        }
    }**/
}
