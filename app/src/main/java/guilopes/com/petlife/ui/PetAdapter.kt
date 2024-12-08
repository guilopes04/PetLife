package guilopes.com.petlife.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import guilopes.com.petlife.R
import guilopes.com.petlife.databinding.TilePetBinding
import guilopes.com.petlife.model.Pet

class PetAdapter(
    context: Context,
    private val petList: MutableList<Pet>,
) : ArrayAdapter<Pet>(context, R.layout.tile_pet, petList) {
    private data class PetTileHolder(
        val nameTv: android.widget.TextView,
        val typeTv: android.widget.TextView,
    )

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        val pet = petList[position]
        var petTile = convertView
        if (petTile == null) {
            val tpb =
                TilePetBinding.inflate(
                    context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    parent,
                    false,
                )
            petTile = tpb.root
            val newPetTileHolder = PetTileHolder(tpb.petNameTv, tpb.petTypeTv)
            petTile.tag = newPetTileHolder
        }
        val holder = petTile.tag as PetTileHolder
        holder.nameTv.text = pet.name
        holder.typeTv.text = pet.type
        return petTile
    }
}
