package guilopes.com.petlife

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private val binding: ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)

        binding.btnViewPet.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditPet.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditVetVisit.setOnClickListener {
            val intent = Intent(this, EditVeterinarianActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditVaccination.setOnClickListener {
            val intent = Intent(this, EditVaccinationActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditPetShopVisit.setOnClickListener {
            val intent = Intent(this, EditPetShopActivity::class.java)
            startActivity(intent)
        }
    }
}
