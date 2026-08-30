package info.alihabibi.common.banks

import androidx.annotation.DrawableRes
import info.alihabibi.common.R

enum class Bank(
    @get:DrawableRes val iconResId: Int
) {

    ANSAR(R.drawable.ansar),
    BLUE_BANK(R.drawable.blu),
    DEY(R.drawable.dey),
    EGHTESAD_NOVIN(R.drawable.eghtesad_novin),
    GARDESHGARI(R.drawable.ghavamin),
    GHAVAMIN(R.drawable.ghavamin),
    HEKMAT_IRANIAN(R.drawable.hekmat_iranian),
    IRAN_ZAMIN(R.drawable.iran_zamin),
    KARAFARIN(R.drawable.karafarin),
    KESHAVARZI(R.drawable.keshavarzi),
    KHAVARMINEH(R.drawable.khavar_mianeh),
    MASKAN(R.drawable.maskan),
    MEHR_EGHTESAD(R.drawable.mehr_eghtesad),
    MEHR_IRAN(R.drawable.mehr_iran),
    MELLAT(R.drawable.mellat),
    MELLI(R.drawable.melli),
    PARSIAN(R.drawable.parsian),
    PASARGAD(R.drawable.pasargad),
    POST_BANK(R.drawable.post),
    REFH(R.drawable.refah),
    RESALAT(R.drawable.resalat),
    SADERAT(R.drawable.saderat),
    SAMAN(R.drawable.saman),
    SANAT_O_MADAN(R.drawable.sanat_madan),
    SARMAYEH(R.drawable.sarmayeh),
    SEPAH(R.drawable.sepah),
    SHAHR(R.drawable.shahr),
    SINA(R.drawable.sina),
    TEJARAT(R.drawable.tejarat),
    TOSSE_SADERAT(R.drawable.tosee_saderat),
    TOSSE_TAAVON(R.drawable.tosee_taavon);

}