package info.alihabibi.domain.local.usecases.database.channel.usecase

import info.alihabibi.domain.local.repositories.ChannelRepository

class UpdateChannelBalanceUseCase(
    private val repository: ChannelRepository
) {

    suspend operator fun invoke(id: Long, balance: Long, isIncome: Boolean) =
        if (isIncome) repository.increaseChannelBalance(id, balance)
        else repository.decreaseChannelBalance(id, balance)

}