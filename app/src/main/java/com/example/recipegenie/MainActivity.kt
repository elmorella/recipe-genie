package com.example.recipegenie

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.view.FavoritesList
import com.example.recipegenie.view.RecipePage
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeAdapter
import com.example.recipegenie.viewmodel.RecipeListGenerator

class MainActivity : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()

    //    var vm = MainViewModel(application)
////    lateinit var adapter: RecipeAdapter
//////
////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["keyValue"]
        val key = value.toString()
        val host = "X-RapidAPI-Host: tasty.p.rapidapi.com"

        println("My key: $key")
//Test Room DB
        var categoryTextView: TextView = findViewById(R.id.text_view_categories)
        var favoritesTextView: TextView = findViewById(R.id.text_view_favorites)
        var viewModel = MainViewModel(application)
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

        viewModel.insertRecipes(recipe)

//        favoritesTextView.text = recipeList[0].title
        // From Room db - as LiveData<RecipesList>>
        viewModel.recipeList?.observe(this) { recipeList ->
            getRecipe(recipeList)
            var myRecipeTitle = recipeList[0].title
            categoryTextView.text = myRecipeTitle
            Log.d("MainActivity", "DB recipeList detected")
        }
        viewModel.getSearchResults(0, 5, "under_30_minutes", "chicken")


        // From API - MutableLiveData<RecipeResult>
        var apiRecipeList = ArrayList<Recipe>()
        viewModel.searchResults.observe(this) {

            var recipeGenerator = RecipeListGenerator()
            apiRecipeList = recipeGenerator.makeList(it)

            // apiRecipeList holds a ArrayList<Recipe>
            var str: String = apiRecipeList[0].title

            favoritesTextView.text = str
        }




////    val apiClient = RetrofitClient.create()
////    val repo = RecipeRepository(apiClient)
//
//    vm = MainViewModel(application)
//   recipeList = vm.getSearchResults(0, 1, "", "meatloaf").
//
////    {
//        //attach adapter
//        var str : String
//        try {
//            str = it[0].title
//            textView.text = str
//            println("inside try. name: $str")
//        } catch (e:Exception){
//            println("inside catch")
//            textView.text = "ERROR"
//        }
//
//        println("inside Main observer")
//    }


        ////
//        vm = MainViewModel(application)
////
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
//        recyclerView.layoutManager = LinearLayoutManager(
//            this, LinearLayoutManager.HORIZONTAL,
//            false
//        )
////
//        adapter = RecipeAdapter({ position -> onCardClick(position) }, recipeList)
//        recyclerView.adapter = adapter
////
//        vm.allRecipes?.observe(this, { recipeList ->
//            getRecipe(recipeList)
//        })
//        //
//        vm = ViewModelProvider(this)[MainViewModel::class.java]
//
////        val linkFavList: View = findViewById(R.id.fav_view)
////        linkFavList.setOnClickListener{
////            val intent = Intent(this, FavoritesList::class.java)
////            startActivity(intent)
////        }
    }

    //
//    fun onCardClick(position: Int) {
//        println("position:::$position")
//        val myIntent = Intent(this, RecipePage::class.java)
//        startActivity(myIntent)
//    }
//
    fun getRecipe(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
//            adapter.notifyDataSetChanged()
    }

}

