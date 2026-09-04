package info.alihabibi.domain.local.usecases.database.channel

import info.alihabibi.domain.local.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow

class GetTotalBalanceUseCase(
    private val repository: ChannelRepository
) {

    operator fun invoke(): Flow<Long> = repository.getTotalBalance()

}