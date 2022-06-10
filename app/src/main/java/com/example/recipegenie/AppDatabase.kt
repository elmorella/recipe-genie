package com.example.recipegenie


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [Recipe::class,com.example.recipegenie.Users::class], version = 2, exportSchema = false)
//
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun recipeDao(): RecipeDao
//    abstract fun usersDao(): UsersDao
//
//    companion object {
//        var INSTANCE: AppDatabase? = null
//        fun getInstance(context: Context): AppDatabase? {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java, "recipe.db"
//                    )
//                        .allowMainThreadQueries().build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
//}

@Database(entities=[Users::class], version = 1, exportSchema = false)
//abstract class and extend
abstract class AppDatabase : RoomDatabase() {
    // 4 abstract and return dao
    abstract fun usersDao():UsersDao
    //5 singleton
    companion object{
        var INSTANCE:AppDatabase?=null
        fun getInstance(context: Context):AppDatabase?{
            // 6 acquiring an instance of RoomDb builder
            if(INSTANCE==null){
                synchronized(AppDatabase::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"users.db")
                        .allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE=null
        }
    }


}


