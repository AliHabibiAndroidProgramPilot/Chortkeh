package info.alihabibi.domain.local.repositories

interface SmsSendersSourceRepository {

    suspend fun getRecentSmsSenders(): Map<String, String>

}