package info.alihabibi.common.banks

object BankCardIdentifier {

    private data class PrefixEntry(val prefix: String, val bank: Bank)

    // WEPOD and BLU should stay at top for correct functionality
    private val cardNumberPrefixes: List<PrefixEntry> = listOf(
        PrefixEntry("62198619", Bank.BLU_BANK),
        PrefixEntry("62198618", Bank.BLU_BANK),
        PrefixEntry("50222915", Bank.WEPOD),
        PrefixEntry("627381", Bank.ANSAR),
        PrefixEntry("502938", Bank.DEY),
        PrefixEntry("627412", Bank.EGHTESAD_NOVIN),
        PrefixEntry("505416", Bank.GARDESHGARI),
        PrefixEntry("639599", Bank.GHAVAMIN),
        PrefixEntry("636949", Bank.HEKMAT_IRANIAN),
        PrefixEntry("581874", Bank.IRAN_VENEZUELA),
        PrefixEntry("505785", Bank.IRAN_ZAMIN),
        PrefixEntry("627488", Bank.KARAFARIN),
        PrefixEntry("502910", Bank.KARAFARIN),
        PrefixEntry("603770", Bank.KESHAVARZI),
        PrefixEntry("585947", Bank.KHAVARMIANEH),
        PrefixEntry("628023", Bank.MASKAN),
        PrefixEntry("606373", Bank.MEHR_IRAN),
        PrefixEntry("606256", Bank.MELAL),
        PrefixEntry("610433", Bank.MELLAT),
        PrefixEntry("991975", Bank.MELLAT),
        PrefixEntry("603799", Bank.MELLI),
        PrefixEntry("622106", Bank.PARSIAN),
        PrefixEntry("639194", Bank.PARSIAN),
        PrefixEntry("627884", Bank.PARSIAN),
        PrefixEntry("502229", Bank.PASARGAD),
        PrefixEntry("639347", Bank.PASARGAD),
        PrefixEntry("627760", Bank.POST_BANK),
        PrefixEntry("589463", Bank.REFAH),
        PrefixEntry("504172", Bank.RESALAT),
        PrefixEntry("603769", Bank.SADERAT),
        PrefixEntry("621986", Bank.SAMAN),
        PrefixEntry("627961", Bank.SANAT_O_MADAN),
        PrefixEntry("639607", Bank.SARMAYEH),
        PrefixEntry("589210", Bank.SEPAH),
        PrefixEntry("639370", Bank.SEPAH),
        PrefixEntry("504706", Bank.SHAHR),
        PrefixEntry("639346", Bank.SINA),
        PrefixEntry("627353", Bank.TEJARAT),
        PrefixEntry("627648", Bank.TOSSE_SADERAT),
        PrefixEntry("207177", Bank.TOSSE_SADERAT),
        PrefixEntry("502908", Bank.TOSSE_TAAVON)
    )

    fun identify(digits: String): Bank = cardNumberPrefixes.first { it.prefix.startsWith(digits) }.bank

}