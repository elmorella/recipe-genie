package com.example.recipegenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.view.FavoritesList
import com.example.recipegenie.view.SearchRecipes
import com.example.recipegenie.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recipeList = ArrayList<Recipe>()
        var recipe = Recipe(
            null,
            false,
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

        recipeList.add(recipe)


        var vm = MainViewModel(application)
        vm.insertRecipes(recipe)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
//
//        var adapter = RecipeAdapter({ position -> onCardClick(position) }, recipeList)
//        recyclerView.adapter = adapter

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
            val myIntent = Intent(this, FavoritesList::class.java)
            startActivity(myIntent)
        }
    }

//    fun onCardClick(position: Int) {
//        println("position:::$position")
//        val myIntent = Intent(this, RecipePage::class.java)
//        startActivity(myIntent)
//    }

//    fun getRecipe(recipeList: List<Recipe>) {
//        this.recipeList.clear()
//        this.recipeList.addAll(recipeList)
//        adapter.notifyDataSetChanged()
//    }
}

