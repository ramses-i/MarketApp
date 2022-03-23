package com.riac.marketapp.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riac.marketapp.R
import com.riac.marketapp.util.Resource
import com.riac.marketapp.databinding.FragmentDetailBinding
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.presentation.MainViewModel
import com.riac.marketapp.presentation.adapters.ListAdapter
import com.riac.marketapp.util.Constants

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rvAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        retrieveSelectedItemInformation()
        setupRecentViewedItemsRecyclerView()
    }

    private fun setupRecentViewedItemsRecyclerView() {
        mainViewModel.getRecentlyViewedItems()
        mainViewModel.recentItems.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    manageProgressBar()
                }
                is Resource.Error -> {
                    manageProgressBar(false)
                    response.message?.let { message ->
                        Log.e(
                            tag,
                            "An Error occurred in method setupRecentViewedItemsRecyclerView() at $tag : $message"
                        )
                    }
                }
                is Resource.Success -> {
                    manageProgressBar(false)
                    response.data?.let {
                        rvAdapter.differ.submitList(it)
                    }
                }
            }
        }
    }

    private fun retrieveSelectedItemInformation() {
        mainViewModel.selectedItem.value?.let {
            mainViewModel.searchItem(it.id)
        }
        mainViewModel.searchItem.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    manageProgressBar()
                }
                is Resource.Error -> {
                    manageProgressBar(false)
                    response.message?.let { message ->
                        Log.e(
                            tag,
                            "An Error occurred in method retrieveSelectedItemInformation() at $tag : $message"
                        )
                    }
                }
                is Resource.Success -> {
                    manageProgressBar(false)
                    response.data?.let { displayInformation(it) }
                }
            }
        }

    }

    private fun displayInformation(itemDetail: ItemDetail) {
        binding.dtPrTitle.text = itemDetail.title.split(",")[0]
        binding.dtPrImgSld.setImageList(itemDetail.pictures)
        binding.dtPrDescription.text = itemDetail.title
        val text = getString(R.string.price_tag, itemDetail.price, itemDetail.currency_id)
        binding.dtPrPrice.text = text
    }

    private fun manageProgressBar(isVisible: Boolean = true) {
        binding.progressBar.isVisible = isVisible
    }

    private fun setupRecyclerView() {
        rvAdapter = ListAdapter(Constants.ORIENTATION_HORIZONTAL)
        binding.rvRecentlyViewed.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}