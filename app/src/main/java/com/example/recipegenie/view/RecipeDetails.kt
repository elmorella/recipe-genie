package com.example.recipegenie.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class RecipeDetails : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_page)

        mainViewModel = MainViewModel(application)
        val recipe: Recipe = intent.getParcelableExtra("recipe")!!

        populateFields(recipe)

        val btnHome: ExtendedFloatingActionButton = findViewById(R.id.btn_cancel)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnEdit: ExtendedFloatingActionButton = findViewById(R.id.btn_edit)
        btnEdit.setOnClickListener {
            val intent: Intent = Intent(this, UpdateRecipe::class.java)
            intent.putExtra("recipe", recipe)
            startActivity(intent)
        }

        val btnDelete: ExtendedFloatingActionButton = findViewById(R.id.btn_delete)
        btnDelete.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("This will permanently delete '$title'. Do you want to continue?")
                .setPositiveButton("Cancel", null)
                .setNegativeButton("Delete") { dialog, which ->
                    mainViewModel.deleteRecipe(recipe)
                    val intent: Intent = Intent(this, RecipeListActivity::class.java)
                    startActivity(intent)
                }
                .show()
        }

        if (!recipe.isFavorite) {
            btnEdit.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }

        val crashlytics = Firebase.crashlytics

        val fav = findViewById<ImageView>(R.id.icon_is_fav)
        fav.setOnClickListener {
            try {
                if (!recipe.isFavorite) {
                    fav.setImageResource(R.drawable.fav_heart_foreground)
                    recipe.isFavorite = true
                    mainViewModel.insertRecipes(recipe)
                } else {
                    mainViewModel.deleteRecipe(recipe)
                }
            } catch (e: Exception) {
                crashlytics.recordException(e)
            }
        }
    }

//    fun getDataFromDB(viewModel:MainViewModel): Recipe {
//        // Map TextViews in recipe page
//        var id: TextView = findViewById(R.id.id)
//        var title: TextView = findViewById(R.id.title)
//        var yields: TextView = findViewById(R.id.yields)
//        var prepTime: TextView = findViewById(R.id.prep_time)
//        var totalTime: TextView = findViewById(R.id.total_time)
//        var ingredients: TextView = findViewById(R.id.ingredients)
//        var directions: TextView = findViewById(R.id.directions)
//        var iconIsFavorite: ImageView = findViewById(R.id.icon_is_fav)
//
//        // Get recipe from repo with ID
//        var recipe: List<Recipe> =
//            viewModel.findRecipeWithTitle(intent.getStringExtra("title").toString())
//
//        //TODO: Null validation
//
////        // Populate Text Views with recipe fields
////        id.text = recipe[0].recipeId.toString()
////        title.text = recipe[0].title
////        yields.text = recipe[0].yields
////        prepTime.text = recipe[0].prepTime
////        totalTime.text = recipe[0].totalTime
////        ingredients.text = recipe[0].ingredients
////        directions.text = recipe[0].directions
//
//        //TODO need to calculate cook time and URL
//
//        return Recipe(
//            id.text.toString().toInt(), false, title.text.toString(), yields.text.toString(),
//            prepTime.text.toString(), "Need to calculate", totalTime.text.toString(),
//            ingredients.text.toString(), directions.text.toString(), "get URL"
//        )
//    }

    fun populateFields(recipe: Recipe) {
        // Map TextViews in recipe page
        val id: TextView = findViewById(R.id.id)
        val title: TextView = findViewById(R.id.title)
        val yields: TextView = findViewById(R.id.yields)
        val prepTime: TextView = findViewById(R.id.prep_time)
        val totalTime: TextView = findViewById(R.id.total_time)
        val ingredients: TextView = findViewById(R.id.ingredients)
        val directions: TextView = findViewById(R.id.directions)
        val recipePhoto: ImageView = findViewById(R.id.image_recipe_photo)
        val iconIsFavorite: ImageView = findViewById(R.id.icon_is_fav)

        id.text = recipe.recipeId.toString()
        title.text = recipe.title
        yields.text = recipe.yields
        prepTime.text = recipe.prepTime
        totalTime.text = recipe.totalTime
        ingredients.text = recipe.ingredients
        directions.text = recipe.directions
        recipePhoto.load(recipe.imageUrl)


        if (recipe.isFavorite) {
            iconIsFavorite.setImageResource(R.drawable.fav_heart_foreground)
        } else {
            iconIsFavorite.setImageResource(R.drawable.fav_heart_gray)
        }
    }
}

