package com.riac.marketapp.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.riac.marketapp.R
import com.riac.marketapp.util.Resource
import com.riac.marketapp.databinding.FragmentSearchBinding
import com.riac.marketapp.presentation.MainViewModel
import com.riac.marketapp.presentation.adapters.ListAdapter
import com.riac.marketapp.util.Constants
import com.riac.marketapp.util.showToast

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rvAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupRecyclerViewActions()
    }

    private fun setupSearchView() {
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                handleSearch(p0.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRecyclerViewActions() {
        rvAdapter.setOnItemClickListener {
            binding.searchField.setQuery("", false)
            mainViewModel.saveLastViewedItem(it)
            mainViewModel.setSelectedItem(it)
            findNavController().navigate(R.id.action_SearchFragment_to_DetailFragment)
        }
    }

    private fun handleSearch(query: String) {
        mainViewModel.makeSearch(query)
        loadData()
    }

    private fun loadData() {
        mainViewModel.searchQuery.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    manageProgressBar()
                }
                is Resource.Error -> {
                    manageProgressBar(false)
                    response.message?.let { message ->
                        Log.e(tag, "An Error occurred in method loadData() at $tag : $message")
                    }
                }
                is Resource.Success -> {
                    manageProgressBar(false)
                    response.data?.let {
                        if (it.results.isNullOrEmpty()) {
                            requireContext().showToast("We could not find results related to: ${binding.searchField.query}")
                        } else {
                            rvAdapter.differ.submitList(it.results)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = ListAdapter(Constants.ORIENTATION_VERTICAL)
        binding.rvSearch.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun manageProgressBar(isVisible: Boolean = true) {
        binding.progressBar.isVisible = isVisible
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

}