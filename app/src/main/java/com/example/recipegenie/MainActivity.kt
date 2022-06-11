package com.example.recipegenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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


class MainActivity : AppCompatActivity() {
    //    //
    var recipeList = ArrayList<Recipe>()
//    var vm = MainViewModel(application)
////    lateinit var adapter: RecipeAdapter
//////
////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        var categoryTextView: TextView = findViewById(R.id.text_view_categories)
        var favoritesTextView: TextView = findViewById(R.id.text_view_favorites)
        var viewModel = MainViewModel(application)
        var recipe = Recipe(
            null, "sample recipe 1", "6 servings",
            "10 mins", "35 mins", "1 whole chicken",
            "Cook it well"
        )

        viewModel.insertRecipes(recipe)
        viewModel.recipeList?.observe(this) { recipeList ->
            getRecipe(recipeList)


            var myRecipeTitle = recipeList[0].title
            categoryTextView.text = myRecipeTitle
            Log.d("MainActivity", "DB recipeList detected")
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
