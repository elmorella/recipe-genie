package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeListGenerator
import com.example.recipegenie.viewmodel.adapters.RecipeListAdapter

class SearchRecipes : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()
    lateinit var mainViewModel: MainViewModel
    lateinit var recipeAdapter: RecipeListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)

        val navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)
        val navBntHome: View = findViewById(R.id.nav_btn_home)
        val navBtnAdd: View = findViewById(R.id.nav_btn_add)

        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recyclerView_favorites_card)

        mainViewModel = MainViewModel(application)
        mainViewModel.getSearchResults(0, 20, "", "")
        val mldSearchResults = mainViewModel.searchResults

        mldSearchResults.observe(this) {
            val recipeListGenerator = RecipeListGenerator()
            recipeList = recipeListGenerator.makeList(it)
            recipeAdapter.setItems(recipeList)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        recipeAdapter = RecipeListAdapter(recipeList, { position -> onCardClick(position) })

        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                mainViewModel.getSearchResults(0, 50, "", query!!)
                val mutableLiveData = mainViewModel.searchResults

                mutableLiveData.observe(this@SearchRecipes) {
                    val recipeListGenerator = RecipeListGenerator()
                    recipeList = recipeListGenerator.makeList(it)
                    recipeAdapter.setItems(recipeList)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        recyclerView.adapter = recipeAdapter

        // Buttons
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
        myIntent.putExtra("recipe", recipeList[position])

        startActivity(myIntent)
    }

    private fun getRecipes(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
        recipeAdapter.notifyDataSetChanged()
    }
}