package com.example.todoapp.data.local

import androidx.room.*
import com.example.todoapp.data.model.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoItem): Long

    @Update
    suspend fun update(item: ToDoItem)

    @Delete
    suspend fun delete(item: ToDoItem)

    @Query("SELECT * FROM tasks ORDER BY isCompleted ASC, dueAt IS NULL, dueAt ASC")
    fun getAll(): Flow<List<ToDoItem>>

    @Query("SELECT * FROM tasks WHERE isCompleted = :completed ORDER BY dueAt ASC")
    fun getByStatus(completed: Boolean): Flow<List<ToDoItem>>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ToDoItem?

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)
}
