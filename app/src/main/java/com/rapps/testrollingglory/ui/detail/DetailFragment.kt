package com.rapps.testrollingglory.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rapps.testrollingglory.R
import com.rapps.testrollingglory.databinding.FragmentDetailBinding
import com.rapps.testrollingglory.utils.htmlFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    private lateinit var idGift: String
    private var _binding: FragmentDetailBinding? = null
    val binding get() = _binding!!
    var qty = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        idGift = DetailFragmentArgs.fromBundle(requireArguments()).giftId
        viewModel.getDetail(idGift)

        observeViewModel()


        binding.apply {
            tvQty.text = qty.toString()

            btnMinus.setOnClickListener {
                if (qty < 2) {
                    qty = 1
                } else {
                    qty--
                }
                tvQty.text = qty.toString()
            }

            btnPlus.setOnClickListener {
                qty++
                tvQty.text = qty.toString()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.detail.observe(viewLifecycleOwner, {
            binding.apply {
                tvTitle.text = it.attributes.name
                tvInfo.text = htmlFormat(it.attributes.info)
                tvDescription.text = htmlFormat(it.attributes.description)
                lytAddtionalOption.rbRating.rating = it.attributes.rating
                lytAddtionalOption.tvReviewCount.text = "${it.attributes.numOfReviews} \n Reviews"
                lytAddtionalOption.tvPoint.text = "${it.attributes.points} \n Points"

                when (it.attributes.isNew) {
                    0 -> ivNew.isVisible = false
                    else -> ivNew.isVisible = true
                }

                when (it.attributes.isWishlist) {
                    0 -> lytAddtionalOption.ivWishlist.isSelected = false
                    else -> lytAddtionalOption.ivWishlist.isSelected = true
                }

                Glide.with(requireContext()).load(it.attributes.images[0]).into(ivGift)

                lytAddtionalOption.ivWishlist.setOnClickListener {
                    val builder =
                        AlertDialog.Builder(requireContext())
                    builder.setTitle("Attention")
                    builder.setMessage("Are you sure to add / remove whishlist ")
                    builder.setNegativeButton("Sure") { dialogInterface, i ->
                        dialogInterface.dismiss()
                        viewModel.setWishlist(idGift)
                    }
                    builder.show()

                }
            }
        })

        viewModel.responseCode.observe(viewLifecycleOwner, { code ->
            when (code) {
                200 -> {
                    viewModel.getDetail(idGift)
                    Snackbar.make(requireContext(), requireView(), "Success", Snackbar.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Snackbar.make(requireContext(), requireView(), "Error", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}