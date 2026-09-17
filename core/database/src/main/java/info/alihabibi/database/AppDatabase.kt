package info.alihabibi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.alihabibi.database.converters.CategoryIconConverter
import info.alihabibi.database.converters.CategoryTypeConverter
import info.alihabibi.database.converters.ChannelIconConverter
import info.alihabibi.database.converters.TransactionTypeConverter
import info.alihabibi.database.dao.CategoryDao
import info.alihabibi.database.dao.ChannelDao
import info.alihabibi.database.dao.TransactionDao
import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.database.entities.ChannelEntity
import info.alihabibi.database.entities.TransactionEntity

@Database(
    entities = [CategoryEntity::class, ChannelEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CategoryIconConverter::class,
    CategoryTypeConverter::class,
    ChannelIconConverter::class,
    TransactionTypeConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun channelDao(): ChannelDao
    abstract fun transactionDao(): TransactionDao

}