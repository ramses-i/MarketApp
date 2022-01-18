package com.riac.marketapp.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riac.marketapp.R
import com.riac.marketapp.common.Resource
import com.riac.marketapp.databinding.FragmentDetailBinding
import com.riac.marketapp.databinding.FragmentSearchBinding
import com.riac.marketapp.domain.model.Item
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.presentation.MainViewModel
import com.riac.marketapp.presentation.adapters.ListAdapter

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rvAdapter: ListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
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
        mainViewModel.recentItems.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(
                            tag,
                            "An Error occurred in method setupRecentViewedItemsRecyclerView() at $tag : $message"
                        )
                    }
                }
                is Resource.Success -> {
                    response.data?.let {
                        rvAdapter.differ.submitList(it)
                    }
                }
            }
        })
    }


    private fun retrieveSelectedItemInformation() {
        mainViewModel.selectedItem.value.let {
            if (it != null) {
                mainViewModel.searchItem(it.id)
            }
        }
        mainViewModel.searchItem.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(
                            tag,
                            "An Error occurred in method retrieveSelectedItemInformation() at $tag : $message"
                        )
                    }
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { displayInformation(it) }
                }
            }
        })

    }

    private fun displayInformation(itemDetail: ItemDetail) {
        binding.dtPrTitle.text = itemDetail.title.split(",")[0]
        binding.dtPrImgSld.setImageList(itemDetail.pictures)
        binding.dtPrDescription.text = itemDetail.title
        binding.dtPrPrice.text = "${itemDetail.price} ${itemDetail.currency_id}"
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }


    private fun setupRecyclerView() {
        rvAdapter = ListAdapter("Horizontal")
        binding.rvRecentlyViewed.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}