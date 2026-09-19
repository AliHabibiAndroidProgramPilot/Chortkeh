package info.alihabibi.domain.local.coordinators

import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import kotlinx.coroutines.launch

class TransactionUndoManager(
    private val transactionUseCases: TransactionUseCases,
    private val channelsUseCase: ChannelUseCases,
    private val applicationScope: ApplicationScope
) {

    fun executeUndo(transactionId: Long, channelId: Long, amount: String, isIncome: Boolean) {
        applicationScope.launch {
            transactionUseCases.deleteTransactionUseCase.invoke(transactionId)
        }
        applicationScope.launch {
            // we reverse the isIncome flag here, since this is undo and we want the reverse action
            channelsUseCase.updateChannelBalanceUseCase.invoke(channelId, amount.toLong(), !isIncome)
        }
    }

}