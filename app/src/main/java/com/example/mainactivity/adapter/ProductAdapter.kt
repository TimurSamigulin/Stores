package com.example.mainactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mainactivity.R
import com.example.mainactivity.room.entity.Product

class ProductAdapter(val listener: OnBtnClickListener): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private var products: List<Product> = listOf()

    interface OnBtnClickListener {
        fun onFuvClickListener(product: Product)
        fun onViewClickListener(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductHolder(itemView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct: Product = products[position]
        holder.txtTitle.text = currentProduct.title
        holder.txtOldPrice.text = currentProduct.price.toString()
        holder.txtDiscount.text = currentProduct.discount.toString()
        holder.txtPrice.text = currentProduct.discountPrice.toString()
        holder.txtDescription.text = currentProduct.description
        Glide.with(holder.imgIcon)
            .load(currentProduct.icon)
            .error(R.drawable.no_image)
            .into(holder.imgIcon)
        holder.bindView(currentProduct)
    }


    inner class ProductHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView
        val txtPrice: TextView
        val txtDiscount: TextView
        val txtOldPrice: TextView
        val txtDescription: TextView
        val imgIcon: ImageView
        val imgStar: ImageButton

        init {
            txtTitle = itemView.findViewById(R.id.product_title)
            txtDescription = itemView.findViewById(R.id.product_description)
            txtPrice = itemView.findViewById(R.id.product_price)
            txtDiscount = itemView.findViewById(R.id.product_discount)
            txtOldPrice = itemView.findViewById(R.id.product_old_price)
            imgIcon = itemView.findViewById(R.id.product_image)
            imgStar = itemView.findViewById(R.id.btn_product_fav)
        }

        fun bindView(product: Product) {
            imgStar.setOnClickListener { listener.onFuvClickListener(product) }
            itemView.setOnClickListener { listener.onViewClickListener(product) }
            if (product.favourite) {
                imgStar.setImageResource(R.drawable.ic_star_check_24dp)
            } else {
                imgStar.setImageResource(R.drawable.ic_star_not_check_24dp)
            }
        }
    }
}