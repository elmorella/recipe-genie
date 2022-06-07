package com.example.project2registration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="users")
data class Users(@PrimaryKey(autoGenerate = true) var userId:Int?,
                 @ColumnInfo(name="userName") var userName:String?,
                 @ColumnInfo (name="password")var password:String?,
                @ColumnInfo (name="emailId")var emailId:String?)
