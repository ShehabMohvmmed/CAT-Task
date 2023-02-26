package com.projects.booklibrary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projects.booklibrary.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile //memory visibility and guarantee that the value that is being read, comes from the main memory and not the cpu-cache, so the value in cpu-cache is always considered to be dirty, and It has to be fetched again
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }else {
                synchronized(this){ //(one thread at a time) Any thread that reaches this point, locks the database instance, does the work defined in the code-block of synchronized and releases the lock
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }



}