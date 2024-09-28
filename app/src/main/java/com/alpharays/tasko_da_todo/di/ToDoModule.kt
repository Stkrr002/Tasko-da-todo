package com.alpharays.tasko_da_todo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    fun provideToDoDao(db: ToDoDatabase): ToDoDao = db.toDoDao()
}
