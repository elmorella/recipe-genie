package com.example.recipegenie.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe

class MainActivityAdapter (private var recipeList: List<Recipe>) :
    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int)
    : ViewHolder {

        // inflate a view and return it
        var viewInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card_layout, parent, false)

        return ViewHolder(viewInflater)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add current item to the holder
        val recipe = recipeList[position]
        holder.idTextView.text = recipe.recipeId.toString()
        holder.titleTextView.text = recipe.title
        holder.yieldTextView.text = recipe.yields
        holder.prepTimeTextView.text = recipe.prepTime
        holder.totalTimeTextView.text = recipe.totalTime
        holder.imageUrl.load(recipe.imageUrl)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var idTextView: TextView = view.findViewById(R.id.id)
        var titleTextView: TextView = view.findViewById(R.id.title)
        var yieldTextView: TextView = view.findViewById(R.id.yields)
        var prepTimeTextView: TextView = view.findViewById(R.id.prep_time)
        var totalTimeTextView: TextView = view.findViewById(R.id.total_time)
        var imageUrl: ImageView = view.findViewById(R.id.img_Url)

        override fun onClick(v: View?) {
//            val position = absoluteAdapterPosition
//            onCardClick(position)
        }
    }
    fun setItems(itemList: List<Recipe>){
        this.recipeList = itemList
        notifyDataSetChanged()
    }

//    private fun getRecipes(recipeList: List<Recipe>) {
//        this.recipeList.clear()
//        this.recipeList.addAll(recipeList)
//        recipeAdapter.notifyDataSetChanged()
//    }
}
