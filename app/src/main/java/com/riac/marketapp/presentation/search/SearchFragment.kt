package com.riac.marketapp.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.riac.marketapp.R
import com.riac.marketapp.common.Resource
import com.riac.marketapp.databinding.FragmentSearchBinding
import com.riac.marketapp.presentation.MainViewModel
import com.riac.marketapp.presentation.adapters.ListAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rvAdapter: ListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                handleSearch(p0.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        setupRecyclerViewActions()
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
        mainViewModel.searchQuery.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(
                            tag,
                            "An Error occurred in method loadData() at $tag : $message"
                        )
                    }
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.results.isNullOrEmpty()) {
                            showToast("We could not find results related to: ${binding.searchField.query}")
                        } else {
                            rvAdapter.differ.submitList(it.results)
                        }
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rvAdapter = ListAdapter("Vertical")
        binding.rvSearch.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}