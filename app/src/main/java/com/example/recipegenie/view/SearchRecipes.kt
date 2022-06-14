package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeAdapter
import com.example.recipegenie.viewmodel.RecipeListGenerator

class SearchRecipes : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()
    lateinit var mainViewModel: MainViewModel
    lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)

        var navBtnFavorites : View = findViewById(R.id.nav_btn_favorites)
        var navBntHome : View = findViewById(R.id.nav_btn_home)
        var navBtnAdd : View = findViewById(R.id.nav_btn_add)


        var searchView : SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchView.clearFocus()

                mainViewModel.getSearchResults(0,30,"", query!!)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })

        var listGenerator = RecipeListGenerator()

        mainViewModel.searchResults.observe(this){
            recipeList = listGenerator.makeList(it)
        }

        // create an adapter
       // recipeAdapter = RecipeAdapter({ position -> onCardClick(position) }, recipeList)


        // take the views adapter then assign it to the custom adapter we created
        //recyclerView.adapter = recipeAdapter

        navBtnFavorites.setOnClickListener {
            val myIntent = Intent(this, RecipeListActivity::class.java)
            startActivity(myIntent)
        }
        navBntHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        navBtnAdd.setOnClickListener {
            val myIntent = Intent(this, NewRecipeForm::class.java)
            startActivity(myIntent)
        }
    }

    private fun onCardClick(position: Int) {
        val myIntent = Intent(this, RecipeDetails::class.java)
        myIntent.putExtra("title", recipeList[position].title)
        startActivity(myIntent)
    }
    private fun getRecipes(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
        recipeAdapter.notifyDataSetChanged()
    }

}