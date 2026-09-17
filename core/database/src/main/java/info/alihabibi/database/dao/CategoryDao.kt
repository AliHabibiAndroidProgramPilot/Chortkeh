package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(categories: List<CategoryEntity>): List<Long>

    @Query("SELECT * FROM ${Keys.CATEGORY_TABLE_NAME} ORDER BY id ASC")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("DELETE FROM ${Keys.CATEGORY_TABLE_NAME} WHERE id IN(:ids)")
    suspend fun deleteCategoryById(vararg ids: Long)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

}