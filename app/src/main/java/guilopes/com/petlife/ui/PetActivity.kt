package guilopes.com.petlife.ui

import BrazilianPhoneNumberFormatter
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.R
import guilopes.com.petlife.databinding.ActivityPetBinding
import guilopes.com.petlife.model.Constant
import guilopes.com.petlife.model.Constant.PET
import guilopes.com.petlife.model.Constant.VIEW_MODE
import guilopes.com.petlife.model.Pet
import java.util.Calendar

class PetActivity : AppCompatActivity() {
    private val apb: ActivityPetBinding by lazy {
        ActivityPetBinding.inflate(layoutInflater)
    }

    private val dateFields by lazy {
        listOf(apb.birthDateEt, apb.lastVetVisitEt, apb.lastVaccinationEt, apb.lastPetShopVisitEt)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)
        supportActionBar?.hide()

        val viewMode = intent.getBooleanExtra(VIEW_MODE, false)
        val receivedPet = intent.getParcelableExtra<Pet>(PET)

        receivedPet?.let { pet ->
            with(apb) {
                nameEt.setText(pet.name)
                birthDateEt.setText(pet.birthDate)
                val typeOptions = resources.getStringArray(R.array.animal_types)
                val typeIndex = typeOptions.indexOf(pet.type)
                if (typeIndex >= 0) {
                    typeSpinner.setSelection(typeIndex)
                }
                typeSpinner.isEnabled = !viewMode
                colorEt.setText(pet.color)
                val sizeOptions = resources.getStringArray(R.array.animal_sizes)
                val sizeIndex = sizeOptions.indexOf(pet.size)
                if (sizeIndex >= 0) {
                    sizeSpinner.setSelection(sizeIndex)
                }
                sizeSpinner.isEnabled = !viewMode
                lastVetVisitEt.setText(pet.lastVeterinarianVisit)
                lastVaccinationEt.setText(pet.lastVaccination)
                lastPetShopVisitEt.setText(pet.lastPetShopVisit)
                veterianPhoneEt.setText(pet.veterianPhone)
                veterianSiteEt.setText(pet.veterianSite)
                nameEt.isEnabled = !viewMode
                birthDateEt.isEnabled = !viewMode
                colorEt.isEnabled = !viewMode
                lastVetVisitEt.isEnabled = !viewMode
                lastVaccinationEt.isEnabled = !viewMode
                lastPetShopVisitEt.isEnabled = !viewMode
                veterianPhoneEt.isEnabled = !viewMode
                veterianSiteEt.isEnabled = !viewMode
                petEventsBt.visibility = if (!viewMode) View.GONE else View.VISIBLE
                saveBt.visibility = if (viewMode) View.GONE else View.VISIBLE
            }
        } ?: run {
            apb.saveBt.visibility = View.VISIBLE
        }

        if (!viewMode) {
            dateFields.forEach { editText ->
                editText.setOnClickListener { showDatePicker(editText) }
            }
        }

        apb.veterianPhoneEt.addTextChangedListener(BrazilianPhoneNumberFormatter(apb.veterianPhoneEt))

        apb.saveBt.setOnClickListener {
            if (!validateFields()) return@setOnClickListener
            val selectedType = apb.typeSpinner.selectedItem?.toString()?.trim()
            val selectedSize = apb.sizeSpinner.selectedItem?.toString()?.trim()
            val pet = Pet(
                id = receivedPet?.id ?: 0,
                name = apb.nameEt.text.toString(),
                birthDate = apb.birthDateEt.text.toString(),
                type = selectedType ?: "",
                color = apb.colorEt.text.toString(),
                size = selectedSize ?: "",
                lastVeterinarianVisit = apb.lastVetVisitEt.text.toString(),
                lastVaccination = apb.lastVaccinationEt.text.toString(),
                lastPetShopVisit = apb.lastPetShopVisitEt.text.toString(),
                veterianPhone = apb.veterianPhoneEt.text.toString(),
                veterianSite = apb.veterianSiteEt.text.toString(),
            )
            Intent().apply {
                putExtra(PET, pet)
                setResult(RESULT_OK, this)
                finish()
            }
        }
        apb.petEventsBt.setOnClickListener {
            Intent(this, PetEventsActivity::class.java).apply {
                putExtra(PET, receivedPet)
                startActivity(this)
            }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, { _, y, m, d ->
            val selectedDate = String.format("%02d/%02d/%04d", d, m + 1, y)
            editText.setText(selectedDate)
        }, year, month, day).show()
    }

    private fun validateFields(): Boolean {
        with(apb) {
            val requiredEditTexts = listOf(
                nameEt,
                birthDateEt,
                colorEt,
                lastVetVisitEt,
                lastVaccinationEt,
                lastPetShopVisitEt,
                veterianPhoneEt,
                veterianSiteEt
            )
            var isValid = true
            requiredEditTexts.forEach {
                if (it.text.toString().trim().isEmpty()) {
                    it.error = "Campo obrigatório"
                    isValid = false
                } else {
                    it.error = null
                }
            }
            val selectedType = typeSpinner.selectedItem?.toString()?.trim()
            val selectedSize = sizeSpinner.selectedItem?.toString()?.trim()
            if (selectedType.isNullOrEmpty()) {
                isValid = false
            }
            if (selectedSize.isNullOrEmpty()) {
                isValid = false
            }
            return isValid
        }
    }
}
