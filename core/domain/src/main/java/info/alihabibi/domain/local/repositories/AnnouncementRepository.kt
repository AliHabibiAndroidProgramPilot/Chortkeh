package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

    suspend fun saveAnnouncement(announcement: Announcement): Long

    suspend fun getTransactionByAnnouncementId(id: Long): Transaction?

    fun getAllSmsAnnouncements(): Flow<List<Announcement>>

    suspend fun hasSmsAnnouncements(): Boolean

    suspend fun deleteAnnouncementById(id: Long)

}
