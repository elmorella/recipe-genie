package com.example.recipegenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.view.LandingPage
import com.example.recipegenie.view.RecipeDetails
import com.example.recipegenie.view.RecipeListActivity
import com.example.recipegenie.view.SearchRecipes
import com.example.recipegenie.viewmodel.adapters.MainActivityAdapter
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeListGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()

    lateinit var adapterFavorites: MainActivityAdapter
    lateinit var adapterCategories: MainActivityAdapter

    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? =null
    var database : FirebaseDatabase? = null

    lateinit var sign_out_btn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_out_btn = findViewById(R.id.sign_out_btn)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        sign_out_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LandingPage::class.java))
            finish()
        }


        var viewModel = MainViewModel(application)

        populateCategoriesView(viewModel, "", "")
        populateFavoritesView(viewModel)

        var btnBreakfast: Button = findViewById(R.id.btn_breakfast)
        var btnLunch: Button = findViewById(R.id.btn_lunch)
        var btnDinner: Button = findViewById(R.id.btn_dinner)

        var navBtnSearch: ImageView = findViewById(R.id.nav_btn_search)
        var navBtnHome: View = findViewById(R.id.nav_btn_home)
        var navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)

        btnBreakfast.setOnClickListener {
            populateCategoriesView(viewModel, "breakfast", "")
        }
        btnLunch.setOnClickListener {
            populateCategoriesView(viewModel, "lunch", "")
        }
        btnDinner.setOnClickListener {
            populateCategoriesView(viewModel, "dinner", "")
        }
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
        adapterFavorites = MainActivityAdapter(recipeList, { position -> onCardClick(position) })
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterFavorites
    }


    fun populateCategoriesView(viewModel: MainViewModel, tag: String, query: String) {
        this.recipeList.clear()
        viewModel.getSearchResults(0, 20, tag, query)
        var mutableLiveData = viewModel.searchResults
        mutableLiveData.observe(this) {
            var recipeListGenerator = RecipeListGenerator()
            recipeList = recipeListGenerator.makeList(it)
            //getRecipes(apiRecipeList)
            adapterCategories.setItems(recipeList)
        }
        adapterCategories = MainActivityAdapter(recipeList, { position -> onCardClick(position) })
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterCategories
    }

    private fun onCardClick(position: Int) {
        val myIntent = Intent(this, RecipeDetails::class.java)
    }


}

































//class MainActivity : AppCompatActivity() {
//     lateinit var adapterFavorites: MainActivityAdapter
//     lateinit var adapterCategories: MainActivityAdapter
//     var favoritesList = ArrayList<Recipe>()
//     var featuredRecipes = ArrayList<Recipe>()
//     var breakFastList = ArrayList<Recipe>()
//     var lunchList = ArrayList<Recipe>()
//     var dinnerList = ArrayList<Recipe>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        var recipe = Recipe(
//            null,
//            true,
//            "Sample Chicken Recipe from DB",
//            "6 servings",
//            "10 mins",
//            "35 mins",
//            "45 mins",
//            "1 whole chicken\n" +
//                    "1/2 Onion\n" +
//                    "3 tomatoes\n" +
//                    "2 potatoes\n" +
//                    "3 carrorts\n" +
//                    "salt and pepper",
//            "Step 1: Prep the chicken\n" +
//                    "Step 2: Prep the veggies\n" +
//                    "Step 3: prep the pot\n" +
//                    "Step 4: Cook it well",
//            "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/" +
//                    "video-api/assets/351171.jpg"
//        )
//
//        var favoritesRecyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites)
//        var categoryRecyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
//        var viewModel = MainViewModel(application)
//        viewModel.insertRecipes(recipe)
//
//        // get favorite recipes from Room DB and attach to Favorites RecyclerView
////        if (favoritesList.isEmpty()) {
//            favoritesList = getFavoritesList(viewModel)
////        }
//        populateFavoritesView(favoritesRecyclerView, favoritesList)
//
//        //get featured recipes
////        if (featuredRecipes.isEmpty()) {
//            featuredRecipes = getRecipesFromApi(viewModel, 0, 20, "", "chicken")
////        }
//        populateCategoriesView(categoryRecyclerView, featuredRecipes)
//
//        //get breakfastList
////        if (breakFastList.isEmpty()) {
//            breakFastList = getRecipesFromApi(viewModel, 0, 20,
//                "breakfast", "")
////        }
//
//        //get lunchList
////        if (lunchList.isEmpty()) {
//            lunchList = getRecipesFromApi(viewModel, 0, 20,
//                "lunch", "")
////        }
//
//        //get dinnerList
////        if (dinnerList.isEmpty()) {
//            dinnerList = getRecipesFromApi(viewModel, 0, 20,
//                "dinner", "")
////        }
//
//
//        var btnBreakfast: Button = findViewById(R.id.btn_breakfast)
//        var btnLunch: Button = findViewById(R.id.btn_lunch)
//        var btnDinner: Button = findViewById(R.id.btn_dinner)
//
//        var navBtnSearch: ImageView = findViewById(R.id.nav_btn_search)
//        var navBtnHome: View = findViewById(R.id.nav_btn_home)
//        var navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)
//
//        btnBreakfast.setOnClickListener {
//            populateCategoriesView(categoryRecyclerView, breakFastList)
//        }
//        btnLunch.setOnClickListener {
//            populateCategoriesView(categoryRecyclerView, lunchList)
//        }
//        btnDinner.setOnClickListener {
//            populateCategoriesView(categoryRecyclerView, dinnerList)
//        }
//        navBtnSearch.setOnClickListener {
//            val myIntent = Intent(this, SearchRecipes::class.java)
//            startActivity(myIntent)
//        }
//        navBtnHome.setOnClickListener {
//            val myIntent = Intent(this, MainActivity::class.java)
//            startActivity(myIntent)
//        }
//        navBtnFavorites.setOnClickListener {
//            val myIntent = Intent(this, RecipeListActivity::class.java)
//            myIntent.putExtra("listSource", "Favorites")
//            startActivity(myIntent)
//        }
//    }
//
//    private fun getRecipesFromApi(viewModel: MainViewModel, offset: Int,
//                                  limit: Int, tags: String, query: String)
//    : ArrayList<Recipe> {
//        var recipeList = ArrayList<Recipe>()
//        var recipeListGenerator = RecipeListGenerator()
//        viewModel.getSearchResults(offset, limit, tags, query)
//        var mutableLiveData = viewModel.searchResults
//        mutableLiveData.observe(this) {
//            recipeList.addAll(recipeListGenerator.makeList(it))
//        }
//        return recipeList
//    }
//
//    private fun storeRecipesFromList(viewModel: MainViewModel, favoritesList: ArrayList<Recipe>) {
//        for(recipe in favoritesList){
//            viewModel.insertRecipes(recipe)
//        }
//    }
//
//    private fun getFavoritesList(viewModel: MainViewModel) : ArrayList<Recipe> {
//        var recipeList = ArrayList<Recipe>()
//        viewModel.getAllRecipes()
//        var liveData = viewModel.recipeList
//        liveData?.observe(this) {
//            recipeList = it as ArrayList<Recipe>
//        }
//        return recipeList
//    }
//
//    private fun populateFavoritesView(recyclerView: RecyclerView, recipeList: ArrayList<Recipe>) {
//        adapterFavorites = MainActivityAdapter(recipeList, { position -> onCardClick(position) })
//        recyclerView.layoutManager = LinearLayoutManager(
//            this, LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        adapterFavorites.setItems(recipeList)
//        recyclerView.adapter = adapterFavorites
//    }
//
//    fun populateCategoriesView(recyclerView: RecyclerView, recipeList: ArrayList<Recipe>) {
//        adapterCategories = MainActivityAdapter(recipeList) { position -> onCardClick(position) }
//        recyclerView.layoutManager = LinearLayoutManager(
//            this, LinearLayoutManager.HORIZONTAL,
//            false
//        )
//
//        adapterCategories.setItems(recipeList)
//        recyclerView.adapter = adapterCategories
//    }
//
//    private fun onCardClick(position: Int) {
//        val myIntent = Intent(this, RecipeDetails::class.java)
//
//        startActivity(myIntent)
//    }
//}

