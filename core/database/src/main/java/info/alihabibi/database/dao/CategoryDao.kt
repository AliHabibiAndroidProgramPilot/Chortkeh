package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.domain.local.keys.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM ${Keys.CATEGORY_TABLE_NAME} ORDER BY id ASC")
    fun getCategories(): Flow<List<CategoryEntity>>

}