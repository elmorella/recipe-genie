package com.example.recipegenie.viewmodel


import android.app.Application
import androidx.lifecycle.*
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeResults
import kotlinx.coroutines.launch

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val repo: RecipeRepository
    val recipeList : LiveData<List<Recipe>>?
    var searchResults = MutableLiveData<RecipeResults>()
    private val _search =MutableLiveData<String>()

    init {
        repo = RecipeRepository(app)
        recipeList = repo.getAllRecipes()
        _search.value = ""
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

    var results = Transformations.switchMap(_search ){ string->
        if(string != ""){
            repo.search(string)
        }else{
            repo.getAllRecipes()
        }
    }

    fun searchIn(text: String) =viewModelScope.launch{
        _search.value = text
    }
}