package info.alihabibi.domain.local.usecases.database.channel.usecase

import info.alihabibi.domain.local.repositories.ChannelRepository

class UpdateChannelBalanceUseCase(
    private val repository: ChannelRepository
) {

    suspend operator fun invoke(id: Long, amount: Long, isIncome: Boolean) =
        if (isIncome) repository.increaseChannelBalance(id, amount)
        else repository.decreaseChannelBalance(id, amount)

}