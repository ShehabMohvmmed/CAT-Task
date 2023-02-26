package com.projects.booklibrary.repository

import androidx.lifecycle.LiveData
import com.projects.booklibrary.data.UserDao
import com.projects.booklibrary.model.User

//Abstracting sources of data from the rest of the app
class UserRepository(private val userDao: UserDao) {

    val readAllData:LiveData<List<User>> = userDao.readAllData() // you get the data using dao abstract method and put here in the repository

    suspend fun addUser(user: User){  // you get the function and use it from the dao
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}