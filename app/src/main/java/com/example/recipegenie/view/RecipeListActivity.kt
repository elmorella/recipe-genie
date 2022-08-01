package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.adapters.RecipeListAdapter


class RecipeListActivity : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()
    lateinit var mainViewModel: MainViewModel
    lateinit var recipeAdapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites_card)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = MainViewModel(application)
        mainViewModel.recipeList?.observe(this) { recipeList ->
            getRecipes(recipeList)
        }

        // create an adapter
        recipeAdapter = RecipeListAdapter(recipeList) { position -> onCardClick(position) }
        // take the views adapter then assign it to the custom adapter we created
        recyclerView.adapter = recipeAdapter

        val search = findViewById<SearchView>(R.id.search_view)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.searchIn(query!!)
                mainViewModel.results.observe(this@RecipeListActivity, Observer { recipeList ->
                    getRecipes(recipeList)
                })
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                mainViewModel.searchIn(query!!)
                mainViewModel.results.observe(this@RecipeListActivity, Observer { recipeList ->
                    getRecipes(recipeList)
                })
                return false
            }
        })

        val navBtnAdd: View = findViewById(R.id.nav_btn_add)
        navBtnAdd.setOnClickListener() {
            val intent = Intent(this, NewRecipeForm::class.java)
            startActivity(intent)
        }

        val navBtnSearch: View = findViewById(R.id.nav_btn_search)
        navBtnSearch.setOnClickListener {
            val myIntent = Intent(this, SearchRecipes::class.java)
            startActivity(myIntent)
        }
        val navBtnHome: View = findViewById(R.id.nav_btn_home)
        navBtnHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
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