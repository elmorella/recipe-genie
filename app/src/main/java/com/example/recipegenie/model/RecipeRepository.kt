package com.example.recipegenie.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeRepository(context: Context) {

    var db: RecipeDao? = AppDatabase.getInstance(context)?.recipeDao()
    var retrofitClient = RetrofitClient.create()
    var searchResults = MutableLiveData<RecipeResults>()

    // Gets recipes from API and returns MutableLiveData<RecipeResults>
    fun getSearchResults(offset: Int, limit: Int, tags: String, query: String) :
    MutableLiveData<RecipeResults>{
        CoroutineScope(Dispatchers.IO).launch {

            var res = retrofitClient.getSearchResults(offset, limit, tags, query)

            if(res.isSuccessful) {
                searchResults.postValue(res.body())
                Log.d("Retrofit Response", "Successful")
            } else {
                Log.d("Retrofit Response", "unsuccessful: RecipeRepository: Line 30")
            }
        }
        return searchResults
    }


    // Gets recipes from Room DB and returns LiveData<List<Recipe>>
    fun getAllRecipes(): LiveData<List<Recipe>>? {

        return db?.selectRecipe()
    }

    fun insertRecipe(recipe: Recipe) {
        db?.insertRecipe(recipe)
    }

    fun updateRecipe(recipe: Recipe) {
        db?.updateRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) {
        db?.deleteRecipe(recipe)
    }

    fun deleteAll() {
        db?.deleteAll()
    }
//
//    fun findRecipeWithId(search: String): List<Recipe> {
//
//        return db?.findRecipeWithId(search)!!
//    }
//
    fun findRecipeWithTitle(search: String): List<Recipe> {

        return db?.findRecipeWithTitle(search)!!
    }

    fun search(searchText:String): LiveData<List<Recipe>>? {
        return db?.search(searchText)
    }
    // insert things in an Async way
}