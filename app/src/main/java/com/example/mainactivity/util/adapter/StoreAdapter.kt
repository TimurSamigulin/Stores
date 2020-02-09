package com.example.mainactivity.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mainactivity.R
import com.example.mainactivity.room.entity.Store
import kotlinx.android.synthetic.main.store_item.view.*

class StoreAdapter(val listener: OnBtnClickListener): RecyclerView.Adapter<StoreAdapter.StoreHolder>() {
    private var stores: List<Store> = listOf()

    interface OnBtnClickListener {
        fun onFuvClickListener(store: Store)
        fun onViewClickListener(store: Store)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        return StoreHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        val currentStore: Store = stores[position]
        holder.textViewTitle.text = currentStore.title
        Glide.with(holder.imageViewIcon)
            .load(currentStore.icon)
            .error(R.drawable.no_image)
            .into(holder.imageViewIcon)
        holder.bindView(currentStore,listener)
    }

    fun setItems(stores: List<Store>) {
        this.stores = stores
        notifyDataSetChanged()
    }


    inner class StoreHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textViewTitle: TextView
        var imageViewIcon: ImageView
        var imageButtonFav: ImageButton
        init {
            textViewTitle = itemView.findViewById(R.id.store_title)
            imageViewIcon = itemView.findViewById(R.id.store_image)
            imageButtonFav = itemView.findViewById(R.id.store_btn_fav)
        }
        fun bindView(store: Store, listener: OnBtnClickListener) {
            imageButtonFav.setOnClickListener {listener.onFuvClickListener(store)}
            itemView.setOnClickListener{listener.onViewClickListener(store)}
            if (store.favourite) {
                imageButtonFav.setImageResource(R.mipmap.star_check)
            } else {
                imageButtonFav.setImageResource(R.mipmap.star_not_check)
            }
        }
    }
}