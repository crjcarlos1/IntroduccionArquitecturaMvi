package com.cralos.introductionmvi.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cralos.introductionmvi.R
import com.cralos.introductionmvi.model.BlogPost
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class MainRecyclerAdapter() : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    private var items: List<BlogPost> = ArrayList()
    private var context: Context? = null

    constructor(context: Context) : this() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_blog_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.blogTitle.text = items.get(position).title
        context?.let { myContext ->
            Glide.with(myContext)
                .load(items.get(position).image)
                .into(holder.blogImage)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newList: List<BlogPost>) {
        items = newList
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blogImage = itemView.blog_image
        val blogTitle = itemView.blog_title
    }

}
