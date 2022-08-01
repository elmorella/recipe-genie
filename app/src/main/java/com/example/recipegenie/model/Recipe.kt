package com.example.recipegenie.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) var recipeId: Int?,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "yields") var yields: String?,
    @ColumnInfo(name = "prepTime") var prepTime: String?,
    @ColumnInfo(name = "cookTime") var cookTime: String?,
    @ColumnInfo(name = "totalTime") var totalTime: String?,
    @ColumnInfo(name = "ingredients") var ingredients: String?,
    @ColumnInfo(name = "directions") var directions: String?,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(recipeId)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(yields)
        parcel.writeString(prepTime)
        parcel.writeString(cookTime)
        parcel.writeString(totalTime)
        parcel.writeString(ingredients)
        parcel.writeString(directions)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}