package com.example.recipegenie.viewmodel


import android.app.Application
import android.app.appsearch.SearchResults
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipegenie.RecipeResults
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.view.FavoritesList
import kotlinx.coroutines.launch

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val repo: RecipeRepository
    val recipeList : LiveData<List<Recipe>>?
    lateinit var searchResults : MutableLiveData<RecipeResults>

    init {
        repo = RecipeRepository(app)
        recipeList = repo.getAllRecipes()
    }

    fun getSearchResults(offset: Int, limit: Int, tags: String, query: String)
    = viewModelScope.launch{

        searchResults = repo.getSearchResults(offset, limit, tags, query)
    }

    fun getAllRecipes() = viewModelScope.launch {
        repo.getAllRecipes()
    }

    fun insertRecipes(recipe: Recipe) = viewModelScope.launch {
        repo.insertRecipe(recipe)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch {
        repo.updateRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repo.deleteRecipe(recipe)
    }

    fun findRecipeWithTitle(search: String): List<Recipe> {

        return repo.findRecipeWithTitle(search)
    }
}