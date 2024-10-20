package guilopes.com.petlife

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityEditVeterinarianBinding

class EditVeterinarianActivity : AppCompatActivity() {

    private val binding: ActivityEditVeterinarianBinding by lazy {
        ActivityEditVeterinarianBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)


        binding.etLastVetVisit.setText(MainActivity.pet.lastVeterinarianVisit)

        binding.btnSave.setOnClickListener {

            MainActivity.pet.lastVeterinarianVisit = binding.etLastVetVisit.text.toString()

            finish()
        }
    }
}

