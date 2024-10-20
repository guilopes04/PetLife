package guilopes.com.petlife

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityEditVaccinationBinding


class EditVaccinationActivity : AppCompatActivity() {

    private val binding: ActivityEditVaccinationBinding by lazy {
        ActivityEditVaccinationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)


        binding.etLastVaccination.setText(MainActivity.pet.lastVaccination)

        binding.btnSave.setOnClickListener {

            MainActivity.pet.lastVaccination = binding.etLastVaccination.text.toString()

            finish()
        }
    }
}

