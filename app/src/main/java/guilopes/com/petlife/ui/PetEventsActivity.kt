package guilopes.com.petlife.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.controller.MainController
import guilopes.com.petlife.databinding.ActivityPetEventsBinding
import guilopes.com.petlife.model.Constant.PET
import guilopes.com.petlife.model.Constant.PET_EVENT
import guilopes.com.petlife.model.Constant.VIEW_MODE
import guilopes.com.petlife.model.Pet
import guilopes.com.petlife.model.PetEvents

class PetEventsActivity : AppCompatActivity() {
    private val amb: ActivityPetEventsBinding by lazy {
        ActivityPetEventsBinding.inflate(layoutInflater)
    }

    private val petEvents: MutableList<PetEvents> = mutableListOf()

    private val petEventsAdapter: PetEventsAdapter by lazy {
        PetEventsAdapter(this, petEvents)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }

    private lateinit var barl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(amb.root)

        barl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val petEvent = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<PetEvents>(PET_EVENT)
                } else {
                    result.data?.getParcelableExtra(PET_EVENT, PetEvents::class.java)
                }

                petEvent?.let { newEvent ->
                    val position = petEvents.indexOfFirst { it.id == newEvent.id }
                    if (position == -1) {
                        val newId = mainController.insertPetEvent(newEvent)
                        val newPetEvent = newEvent.copy(id = newId)
                        petEvents.add(newPetEvent)
                    } else {
                        petEvents[position] = newEvent
                    }
                    petEventsAdapter.notifyDataSetChanged()
                }
            }
        }

        amb.btnAddPetEvent.setOnClickListener {
            val intent = Intent(this, AddPetEventActivity::class.java)
            barl.launch(intent)
        }

        fillPetEvents()

        amb.petLv.adapter = petEventsAdapter

        registerForContextMenu(amb.petLv)
    }

    private fun fillPetEvents() {
        Thread {
            runOnUiThread {
                petEvents.clear()
                petEvents.addAll(mainController.getPetEvents(1))
                petEventsAdapter.notifyDataSetChanged()
            }
        }.start()
    }
}
