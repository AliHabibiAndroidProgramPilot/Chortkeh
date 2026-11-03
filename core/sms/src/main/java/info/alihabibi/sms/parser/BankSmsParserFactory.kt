package info.alihabibi.sms.parser

import info.alihabibi.common.banks.Bank

internal object BankSmsParserFactory {

    fun parsSmsByFactory(normalizedText: String, bankName: String?): ParsedTransaction {
        val bank = resolveBankEnum(bankName)
        val primaryParser = getParserForBank(bank)
        val parsResult = primaryParser.parse(normalizedText)
        return if (parsResult.isEmpty()) UniversalBankFallBackParser.parse(normalizedText) else parsResult
    }

    private fun getParserForBank(bank: Bank): BaseBankSmsParser {
        return when (bank) {
            Bank.BLU_BANK -> BluBankSmsParser
            Bank.PASARGAD -> PasargadSmsParser
            Bank.SHAHR,
            Bank.KARAFARIN,
            Bank.KESHAVARZI,
            Bank.MASKAN,
            Bank.REFAH -> LabeledPriceSmsParser
            Bank.MELLAT,
            Bank.TEJARAT -> ActionLabeledSmsParser
            Bank.SADERAT,
            Bank.MELLI -> SuffixSignedSmsParser
            else -> UniversalBankFallBackParser
        }
    }

    private fun resolveBankEnum(bankName: String?): Bank {
        if (bankName.isNullOrBlank()) return Bank.UNKNOWN
        return Bank.entries.find {
            it.name.equals(bankName, ignoreCase = true)
        } ?: Bank.UNKNOWN
    }

}