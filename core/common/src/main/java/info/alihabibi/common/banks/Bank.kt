package info.alihabibi.common.banks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import info.alihabibi.common.R

enum class Bank(
    @get:DrawableRes val iconResId: Int,
    @get:StringRes val bankNameResId: Int
) {

    ANSAR(
        R.drawable.ansar,
        R.string.bank_ansar
    ),

    BLU_BANK(
        R.drawable.blu,
        R.string.bank_blu
    ),

    DEY(
        R.drawable.dey,
        R.string.bank_dey
    ),

    EGHTESAD_NOVIN(
        R.drawable.eghtesad_novin,
        R.string.bank_eghtesad_novin
    ),

    GARDESHGARI(
        R.drawable.gardeshgari,
        R.string.bank_gardeshgari
    ),

    GHAVAMIN(
        R.drawable.ghavamin,
        R.string.bank_ghavamin
    ),

    HEKMAT_IRANIAN(
        R.drawable.hekmat_iranian,
        R.string.bank_hekmat_iranian
    ),

    IRAN_VENEZUELA(
        R.drawable.iran_venezuela,
R.string.bank_iran_venezuela
    ),

    IRAN_ZAMIN(
        R.drawable.iran_zamin,
        R.string.bank_iran_zamin
    ),

    KARAFARIN(
        R.drawable.karafarin,
        R.string.bank_karafarin
    ),

    KESHAVARZI(
        R.drawable.keshavarzi,
        R.string.bank_keshavarzi
    ),

    KHAVARMIANEH(
        R.drawable.khavar_mianeh,
        R.string.bank_khavarmianeh
    ),

    MASKAN(
        R.drawable.maskan,
        R.string.bank_maskan
    ),

    MEHR_IRAN(
        R.drawable.mehr_iran,
        R.string.bank_mehr_iran
    ),

    MELAL(
        R.drawable.mellal,
        R.string.bank_melal
    ),

    MELLAT(
        R.drawable.mellat,
        R.string.bank_mellat
    ),

    MELLI(
        R.drawable.melli,
        R.string.bank_melli
    ),

    PARSIAN(
        R.drawable.parsian,
        R.string.bank_parsian
    ),

    PASARGAD(
        R.drawable.pasargad,
        R.string.bank_pasargad
    ),

    POST_BANK(
        R.drawable.post,
        R.string.bank_post
    ),

    REFAH(
        R.drawable.refah,
        R.string.bank_refah
    ),

    RESALAT(
        R.drawable.resalat,
        R.string.bank_resalat
    ),

    SADERAT(
        R.drawable.saderat,
        R.string.bank_saderat
    ),

    SAMAN(
        R.drawable.saman,
        R.string.bank_saman
    ),

    SANAT_O_MADAN(
        R.drawable.sanat_madan,
        R.string.bank_sanat_o_madan
    ),

    SARMAYEH(
        R.drawable.sarmayeh,
        R.string.bank_sarmayeh
    ),

    SEPAH(
        R.drawable.sepah,
        R.string.bank_sepah
    ),

    SHAHR(
        R.drawable.shahr,
        R.string.bank_shahr
    ),

    SINA(
        R.drawable.sina,
        R.string.bank_sina
    ),

    TEJARAT(
        R.drawable.tejarat,
        R.string.bank_tejarat
    ),

    TOSSE_SADERAT(
        R.drawable.tosee_saderat,
        R.string.bank_tosee_saderat
    ),

    TOSSE_TAAVON(
        R.drawable.tosee_taavon,
        R.string.bank_tosee_taavon
    ),

    WEPOD(
        R.drawable.wepod,
        R.string.bank_wepod
    )
}