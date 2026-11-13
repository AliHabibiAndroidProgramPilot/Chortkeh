package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.announcement.Announcement
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

    suspend fun saveAnnouncement(announcement: Announcement): Long

    fun getAnnouncementById(id: Long): Flow<Announcement?>
}
