package info.alihabibi.domain.local.usecases.sms

import info.alihabibi.domain.local.repositories.SmsSendersSourceRepository

class GetSmsSendersSourceUseCase(private val repository: SmsSendersSourceRepository) {

    /**
     * returns a linked Map of Sms Senders (inbox numbers) and latest SMS content in that SMS sender
     * map key represents SMS sender, map value represents SMS content
     */
    suspend operator fun invoke(): Map<String, String> = repository.getRecentSmsSenders()

}