package info.alihabibi.database.repositories

import info.alihabibi.database.dao.AnnouncementDao
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.AnnouncementRepository
import info.alihabibi.domain.models.announcement.Announcement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnnouncementRepositoryImpl(private val dao: AnnouncementDao) : AnnouncementRepository {

    override suspend fun saveAnnouncement(announcement: Announcement): Long {
        return withContext(Dispatchers.IO) {
            dao.insertAnnouncement(announcement.asEntity())
        }
    }

    override fun getAnnouncementById(id: Long): Flow<Announcement?> {
        return dao.getAnnouncementById(id)
            .map { entity -> entity?.asExternalModel() }
            .flowOn(Dispatchers.IO)
    }

}
