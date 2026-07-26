package info.alihabibi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.alihabibi.database.converters.CategoryIconConverter
import info.alihabibi.database.converters.CategoryTypeConverter
import info.alihabibi.database.dao.CategoryDao
import info.alihabibi.database.entities.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CategoryIconConverter::class,
    CategoryTypeConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

}