package guilopes.com.petlife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val id: Long = 0,
    var name: String,
    var birthDate: String,
    var type: String,
    var color: String,
    var size: String,
    var lastVeterinarianVisit: String,
    var lastVaccination: String,
    var lastPetShopVisit: String,
    var veterianPhone: String,
    var veterianSite: String,
) : Parcelable
