package guilopes.com.petlife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityEditPetShopBinding

class EditPetShopActivity : AppCompatActivity() {

    private val binding: ActivityEditPetShopBinding by lazy {
        ActivityEditPetShopBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(binding.root)

        binding.etLastPetShopVisit.setText(MainActivity.pet.lastPetShopVisit)

        binding.btnSave.setOnClickListener {

            MainActivity.pet.lastPetShopVisit = binding.etLastPetShopVisit.text.toString()

            finish()
        }
    }
}

