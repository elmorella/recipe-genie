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
import com.example.recipegenie.viewmodel.RecipeListGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.lifecycle.*
import com.example.recipegenie.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var adapterFavorites: MainActivityAdapter
    lateinit var adapterCategories: MainActivityAdapter
    lateinit var auth: FirebaseAuth
    lateinit var sign_out_btn: TextView

    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    var categoryList: List<Recipe> = listOf()
    var favoriteList: List<Recipe> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBreakfast: Button = findViewById(R.id.btn_breakfast)
        val btnLunch: Button = findViewById(R.id.btn_lunch)
        val btnDinner: Button = findViewById(R.id.btn_dinner)
        val navBtnSearch: ImageView = findViewById(R.id.nav_btn_search)
        val navBtnHome: View = findViewById(R.id.nav_btn_home)
        val navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)
        val rvFavorites: RecyclerView = findViewById(R.id.recyclerView_favorites)
        val rvCategories: RecyclerView = findViewById(R.id.recyclerView_category)
        val viewModel = MainViewModel(application)

        sign_out_btn = findViewById(R.id.sign_out_btn)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        getCategoriesList(viewModel, "", "")
        getFavoritesList(viewModel)

        rvCategories.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterCategories =
            MainActivityAdapter(categoryList, { position -> onCategoryCardClick(position) })
        rvCategories.adapter = adapterCategories


        rvFavorites.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterFavorites =
            MainActivityAdapter(favoriteList, { position -> onFavoriteCardClick(position) })
        rvFavorites.adapter = adapterFavorites

        // Buttons
        sign_out_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LandingPage::class.java))
            finish()
        }
        btnBreakfast.setOnClickListener {
            getCategoriesList(viewModel, "breakfast", "")
        }
        btnLunch.setOnClickListener {
            getCategoriesList(viewModel, "lunch", "")
        }
        btnDinner.setOnClickListener {
            getCategoriesList(viewModel, "dinner", "")
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
            startActivity(myIntent)
        }
    }

    private fun getFavoritesList(viewModel: MainViewModel) {
        viewModel.getAllRecipes()
        val liveData = viewModel.recipeList
        liveData?.observe(this) {
            favoriteList = it
            adapterFavorites.setItems(favoriteList)
        }
    }

    private fun getCategoriesList(viewModel: MainViewModel, tags: String, query: String) {
        viewModel.getSearchResults(0, 20, tags, query)
        val mutableLiveData = viewModel.searchResults
        mutableLiveData.observe(this) {
            val recipeListGenerator = RecipeListGenerator()
            categoryList = recipeListGenerator.makeList(it)
            adapterCategories.setItems(categoryList)
        }
    }

    private fun onFavoriteCardClick(position: Int) {
        val intent = Intent(this, RecipeDetails::class.java)
        intent.putExtra("recipe", favoriteList[position])
        startActivity(intent)
    }

    private fun onCategoryCardClick(position: Int) {
        val intent = Intent(this, RecipeDetails::class.java)
        intent.putExtra("recipe", categoryList[position])
        startActivity(intent)
    }
}

