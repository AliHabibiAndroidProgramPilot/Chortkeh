package info.alihabibi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.alihabibi.database.converters.CategoryIconConverter
import info.alihabibi.database.converters.CategoryTypeConverter
import info.alihabibi.database.converters.ChannelIconConverter
import info.alihabibi.database.dao.CategoryDao
import info.alihabibi.database.dao.ChannelDao
import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.database.entities.ChannelEntity

@Database(
    entities = [CategoryEntity::class, ChannelEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CategoryIconConverter::class,
    CategoryTypeConverter::class,
    ChannelIconConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun channelDao(): ChannelDao

}