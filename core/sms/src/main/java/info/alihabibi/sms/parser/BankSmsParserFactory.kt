package info.alihabibi.sms.parser

import info.alihabibi.common.banks.Bank

internal object BankSmsParserFactory {

    fun parsSms(rawSms: String, bankName: String?): ParsedTransaction? {
        val normalizedText = SmsNormalizer.normalizer(rawSms)
        if(!SmsNormalizer.isLikelyTransactionSms(normalizedText)) return null

        val bank = resolveBankEnum(bankName)
        val primaryParser = getParserForBank(bank)

        return primaryParser.parse(normalizedText)
            ?: if (primaryParser !== UniversalBankFallBackParser) {
                UniversalBankFallBackParser.parse(normalizedText)
            } else null
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