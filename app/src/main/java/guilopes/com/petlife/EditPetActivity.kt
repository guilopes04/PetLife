package guilopes.com.petlife

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityEditPetBinding


class EditPetActivity : AppCompatActivity() {

    private val binding: ActivityEditPetBinding by lazy {
        ActivityEditPetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)

        with(binding) {

            etName.setText(MainActivity.pet.name)
            etBirthDate.setText(MainActivity.pet.birthDate)
            etType.setText(MainActivity.pet.type)
            etColor.setText(MainActivity.pet.color)
            etSize.setText(MainActivity.pet.size)

            btnSave.setOnClickListener {

                MainActivity.pet.name = etName.text.toString()
                MainActivity.pet.birthDate = etBirthDate.text.toString()
                MainActivity.pet.type = etType.text.toString()
                MainActivity.pet.color = etColor.text.toString()
                MainActivity.pet.size = etSize.text.toString()

                finish()
            }
        }
    }
}

