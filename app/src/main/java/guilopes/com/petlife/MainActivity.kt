package guilopes.com.petlife

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        var pet = Pet(
            name = "Charlie",
            birthDate = "01/06/2012",
            type = "Cão",
            color = "Cinza e branco",
            size = "Grande",
            lastVeterinarianVisit = "01/06/2020",
            lastVaccination = "15/05/2019",
            lastPetShopVisit = "10/07/2020"
        )
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)

        binding.btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updatePetInfo()
    }

    private fun updatePetInfo() {
        with(binding) {
            tvName.text = pet.name
            tvBirthDate.text = pet.birthDate
            tvType.text = pet.type
            tvColor.text = pet.color
            tvSize.text = pet.size
            tvLastVetVisit.text = pet.lastVeterinarianVisit
            tvLastVaccination.text = pet.lastVaccination
            tvLastPetShopVisit.text = pet.lastPetShopVisit
        }
    }
}
