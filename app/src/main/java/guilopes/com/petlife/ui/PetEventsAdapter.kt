package guilopes.com.petlife.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import guilopes.com.petlife.R
import guilopes.com.petlife.databinding.TilePetEventBinding
import guilopes.com.petlife.model.PetEvents

class PetEventsAdapter(
    context: Context,
    private val petEventList: MutableList<PetEvents>,
) : ArrayAdapter<PetEvents>(context, R.layout.tile_pet_event, petEventList) {
    private data class PetEventHolder(
        val eventTypeTv: android.widget.TextView,
        val eventDateTv: android.widget.TextView,
    )

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        val petEvent = petEventList[position]
        var eventTile = convertView
        if (eventTile == null) {
            val tpeb = TilePetEventBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false,
            )
            eventTile = tpeb.root
            val newEventHolder = PetEventHolder(tpeb.petEventTypeTv, tpeb.petEventDateTv)
            eventTile.tag = newEventHolder
        }
        val holder = eventTile.tag as PetEventHolder
        holder.eventTypeTv.text = petEvent.type
        holder.eventDateTv.text = petEvent.date
        return eventTile
    }
}
