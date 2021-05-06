package com.rapps.testrollingglory.ui.listGift

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rapps.testrollingglory.R
import com.rapps.testrollingglory.data.ListGift
import com.rapps.testrollingglory.databinding.ItemGiftBinding

class GIftListRVAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ListGift, GIftListRVAdapter.GiftViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        val binding = ItemGiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiftViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) {
        val currentItem = getItem(position)

        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class GiftViewHolder(private val itemGiftBinding: ItemGiftBinding) :
        RecyclerView.ViewHolder(itemGiftBinding.root) {

        init {
            itemGiftBinding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(item: ListGift) {
            itemGiftBinding.apply {
                tvGiftName.text = item.attributes.name
                Glide.with(itemView).load(item.attributes.images[0])
                    .transition(DrawableTransitionOptions.withCrossFade()).error(
                        R.drawable.ic_baseline_broken_image_24
                    ).into(ivGift)

                when (item.attributes.isNew) {
                    1 -> ivNew.isVisible = true
                    else -> ivNew.isVisible = false
                }

                when (item.attributes.isWishlist) {
                    0 -> ivWishlist.isSelected = false
                    else -> ivWishlist.isSelected = true
                }

                tvReviewCount.text = "${item.attributes.numOfReviews} reviews"
                tvPoints.text = "${item.attributes.points} points"
                appCompatRatingBar.rating = item.attributes.rating
            }
        }
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListGift>() {
            override fun areItemsTheSame(oldItem: ListGift, newItem: ListGift): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: ListGift, newItem: ListGift): Boolean =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: ListGift)
    }

}