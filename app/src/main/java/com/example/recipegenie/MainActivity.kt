package com.example.recipegenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeResults
import com.example.recipegenie.view.RecipeListActivity
import com.example.recipegenie.view.SearchRecipes
import com.example.recipegenie.viewmodel.adapters.MainActivityAdapter
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeListGenerator

class MainActivity : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()

    lateinit var adapterFavorites: MainActivityAdapter
    lateinit var adapterCategories: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recipe = Recipe(
            null,
            true,
            "Sample Chicken Recipe from DB",
            "6 servings",
            "10 mins",
            "35 mins",
            "45 mins",
            "1 whole chicken\n" +
                    "1/2 Onion\n" +
                    "3 tomatoes\n" +
                    "2 potatoes\n" +
                    "3 carrorts\n" +
                    "salt and pepper",
            "Step 1: Prep the chicken\n" +
                    "Step 2: Prep the veggies\n" +
                    "Step 3: prep the pot\n" +
                    "Step 4: Cook it well",
            "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/" +
                    "video-api/assets/351171.jpg"
        )

        var viewModel = MainViewModel(application)
        viewModel.insertRecipes(recipe)
        populateCategoriesView(viewModel)
        populateFavoritesView(viewModel)


        var navBtnSearch: View = findViewById(R.id.nav_btn_search)
        var navBtnHome: View = findViewById(R.id.nav_btn_home)
        var navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)

        navBtnSearch.setOnClickListener {
            val myIntent = Intent(this, SearchRecipes::class.java)
            startActivity(myIntent)
        }
        navBtnHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        navBtnFavorites.setOnClickListener {
            val myIntent = Intent(this, RecipeListActivity::class.java)
            myIntent.putExtra("listSource", "Favorites")
            startActivity(myIntent)
        }
    }

    private fun populateFavoritesView(viewModel: MainViewModel) {
        this.recipeList.clear()
        viewModel.getAllRecipes()
        var liveData = viewModel.recipeList
        liveData?.observe(this) {
            adapterFavorites.setItems(it)
        }
        adapterFavorites = MainActivityAdapter(recipeList)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterFavorites
    }


    fun populateCategoriesView(viewModel: MainViewModel) {
        this.recipeList.clear()
        viewModel.getSearchResults(0, 20, "dinner", "")
        var mutableLiveData = viewModel.searchResults
        mutableLiveData.observe(this) {
            var recipeListGenerator = RecipeListGenerator()
            recipeList = recipeListGenerator.makeList(it)
            //getRecipes(apiRecipeList)
            adapterCategories.setItems(recipeList)
        }
        adapterCategories = MainActivityAdapter(recipeList)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterCategories
    }
}

