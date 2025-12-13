package info.alihabibi.data

interface DatastoreRepository {

    suspend fun saveIsFirstLaunch(value: Boolean) {



    }

}