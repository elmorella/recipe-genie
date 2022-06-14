package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeAdapter

class FavoritesList : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()
    lateinit var mainViewModel: MainViewModel
    lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_list)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites_card)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = MainViewModel(application)
        mainViewModel.recipeList?.observe(this) { recipeList ->
            getRecipes(recipeList)
        }

            // create an adapter
            recipeAdapter = RecipeAdapter(recipeList, { position -> onCardClick(position) })
            // take the views adapter then assign it to the custom adapter we created
            recyclerView.adapter = recipeAdapter

        var NavBtnAdd : View = findViewById(R.id.nav_btn_add)
        NavBtnAdd.setOnClickListener() {
            val intent = Intent(this, NewRecipeForm::class.java)
            startActivity(intent)
        }

        var NavBtnSearch : View = findViewById(R.id.nav_btn_search)
        NavBtnSearch.setOnClickListener {
            val myIntent = Intent(this, SearchRecipes::class.java)
            startActivity(myIntent)
        }
        var NavBtnHome : View = findViewById(R.id.nav_btn_home)
        NavBtnHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun onCardClick(position: Int) {
        val myIntent = Intent(this, RecipePage::class.java)
        myIntent.putExtra("id", recipeList[position].recipeId)
        myIntent.putExtra("isFavorite", recipeList[position].isFavorite)
        myIntent.putExtra("title", recipeList[position].title)
        myIntent.putExtra("yields", recipeList[position].yields)
        myIntent.putExtra("prepTime", recipeList[position].prepTime)
        myIntent.putExtra("cookTime", recipeList[position].cookTime)
        myIntent.putExtra("totalTime", recipeList[position].totalTime)
        myIntent.putExtra("ingredients", recipeList[position].ingredients)
        myIntent.putExtra("directions", recipeList[position].directions)
        myIntent.putExtra("imageUrl", recipeList[position].imageUrl)

        startActivity(myIntent)
    }

    private fun getRecipes(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
        recipeAdapter.notifyDataSetChanged()
    }
}