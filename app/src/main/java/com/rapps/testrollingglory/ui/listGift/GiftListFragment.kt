package com.rapps.testrollingglory.ui.listGift

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rapps.testrollingglory.R
import com.rapps.testrollingglory.data.ListGift
import com.rapps.testrollingglory.databinding.FragmentGiftListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiftListFragment : Fragment(R.layout.fragment_gift_list),GIftListRVAdapter.OnItemClickListener {

    private val viewModel by viewModels<GiftListViewModel>()

    lateinit var rvAdapter:GIftListRVAdapter

    private var _binding: FragmentGiftListBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGiftListBinding.bind(view)

        initView()
        observeViewModel()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnRetry.setOnClickListener {
                rvAdapter.retry()
            }
        }

        rvAdapter.addLoadStateListener {loadState ->
            binding.apply {
                pbLoading.isVisible = loadState.source.refresh is LoadState.Loading
                rvGiftList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    rvAdapter.itemCount < 1) {
                    rvGiftList.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }

    }

    private fun initView() {
        rvAdapter = GIftListRVAdapter(this)
        binding.apply {
            rvGiftList.apply {
                adapter = rvAdapter
                layoutManager = GridLayoutManager(requireContext(),2)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.listGift.observe(viewLifecycleOwner,{
            rvAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: ListGift) {
        val action = GiftListFragmentDirections.actionGiftListFragmentToDetailFragment(item.id)
        findNavController().navigate(action)
    }
}