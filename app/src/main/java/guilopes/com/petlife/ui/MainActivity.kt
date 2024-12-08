package guilopes.com.petlife.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.R
import guilopes.com.petlife.controller.MainController
import guilopes.com.petlife.databinding.ActivityMainBinding
import guilopes.com.petlife.model.Constant.PET
import guilopes.com.petlife.model.Constant.VIEW_MODE
import guilopes.com.petlife.model.Pet

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val petList: MutableList<Pet> = mutableListOf()

    private val petAdapter: PetAdapter by lazy {
        PetAdapter(this, petList)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }

    private lateinit var barl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(amb.root)

        barl =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val pet =
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                            result.data?.getParcelableExtra<Pet>(PET)
                        } else {
                            result.data?.getParcelableExtra(PET, Pet::class.java)
                        }
                    pet?.let { receivedPet ->
                        val position = petList.indexOfFirst { it.id == receivedPet.id }
                        if (position == -1) {
                            val newId = mainController.insertPet(receivedPet)
                            val newPet = receivedPet.copy(id = newId)
                            petList.add(newPet)
                        } else {
                            petList[position] = receivedPet
                            mainController.modifyPet(receivedPet)
                        }
                        petAdapter.notifyDataSetChanged()
                    }
                }
            }

        amb.btnAddPet.setOnClickListener {
            barl.launch(Intent(this, PetActivity::class.java))
        }

        fillPetList()

        amb.petLv.adapter = petAdapter
        amb.petLv.setOnItemClickListener { _, _, position, _ ->
            Intent(this, PetActivity::class.java).apply {
                putExtra(PET, petList[position])
                putExtra(VIEW_MODE, true)
                startActivity(this)
            }
        }

        registerForContextMenu(amb.petLv)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.editItemMi -> {
                Intent(this, PetActivity::class.java).apply {
                    putExtra(PET, petList[position])
                    putExtra(VIEW_MODE, false)
                    barl.launch(this)
                }
                true
            }
            R.id.removeItemMi -> {
                mainController.removePet(petList[position].id)
                petList.removeAt(position)
                petAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun fillPetList() {
        Thread {
            runOnUiThread {
                petList.clear()
                petList.addAll(mainController.getPets())
                petAdapter.notifyDataSetChanged()
            }
        }.start()
    }
}
