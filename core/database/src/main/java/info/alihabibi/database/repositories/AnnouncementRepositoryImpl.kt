package info.alihabibi.database.repositories

import info.alihabibi.database.dao.AnnouncementDao
import info.alihabibi.database.entities.DetailedAnnouncement
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.AnnouncementRepository
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.domain.models.transaction.Transaction
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

    override suspend fun getTransactionByAnnouncementId(id: Long): Transaction? {
        return withContext(Dispatchers.IO) {
            dao.getTransactionByAnnouncementId(id)?.asExternalModel()
        }
    }

    override fun getAllSmsAnnouncements(): Flow<List<Announcement>> {
        return dao.getAllSmsAnnouncements()
            .map { entity -> entity.map(DetailedAnnouncement::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun hasSmsAnnouncements(): Boolean {
        return withContext(Dispatchers.IO) {
            dao.hasSmsAnnouncements()
        }
    }

    override suspend fun deleteAnnouncementById(id: Long) {
        withContext(Dispatchers.IO) {
            dao.deleteAnnouncementById(id)
        }
    }

}
