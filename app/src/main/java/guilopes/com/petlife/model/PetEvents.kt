package guilopes.com.petlife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetEvents(
    val id: Long = 0,
    val type: String,
    val date: String,
    val hour: String,
    val pet_id: Long = 0
) : Parcelable
