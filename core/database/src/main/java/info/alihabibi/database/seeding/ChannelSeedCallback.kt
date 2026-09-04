package info.alihabibi.database.seeding

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import info.alihabibi.common.ApplicationScope
import info.alihabibi.database.AppDatabase
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.domain.local.defaults.DefaultChannels
import kotlinx.coroutines.launch

/**
 * Lazily resolved — invoked only once the DB file is actually created,
 * by which point Koin's `AppDatabase` single is already built and cached.
 * This is NOT circular despite referencing `AppDatabase` from inside its own builder.
 */

internal class ChannelSeedCallback(
    private val scope: ApplicationScope,
    private val databaseProvider: () -> AppDatabase
) : RoomDatabase.Callback() {

    override fun onCreate(connection: SQLiteConnection) {
        super.onCreate(connection)
        scope.launch {
            val dao = databaseProvider.invoke().channelDao()
            dao.insertChannel(DefaultChannels.channel.asEntity())
        }
    }

}