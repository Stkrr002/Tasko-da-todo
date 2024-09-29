package com.alpharays.tasko_da_todo.di

import android.app.Application
import com.alpharays.tasko_da_todo.data.localdatasource.database.TaskDatabase
import com.alpharays.tasko_da_todo.data.localdatasource.dao.TaskDao
import com.alpharays.tasko_da_todo.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return TaskDatabase.getDatabase(app)
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }
}