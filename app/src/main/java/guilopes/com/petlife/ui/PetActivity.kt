package guilopes.com.petlife.ui

import BrazilianPhoneNumberFormatter
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
                typeEt.setText(pet.type)
                colorEt.setText(pet.color)
                sizeEt.setText(pet.size)
                lastVetVisitEt.setText(pet.lastVeterinarianVisit)
                lastVaccinationEt.setText(pet.lastVaccination)
                lastPetShopVisitEt.setText(pet.lastPetShopVisit)
                veterianPhoneEt.setText(pet.veterianPhone)
                veterianSiteEt.setText(pet.veterianSite)

                nameEt.isEnabled = !viewMode
                birthDateEt.isEnabled = !viewMode
                typeEt.isEnabled = !viewMode
                colorEt.isEnabled = !viewMode
                sizeEt.isEnabled = !viewMode
                lastVetVisitEt.isEnabled = !viewMode
                lastVaccinationEt.isEnabled = !viewMode
                lastPetShopVisitEt.isEnabled = !viewMode
                veterianPhoneEt.isEnabled = !viewMode
                veterianSiteEt.isEnabled = !viewMode

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

            val pet = Pet(
                id = receivedPet?.id ?: 0,
                name = apb.nameEt.text.toString().trim(),
                birthDate = apb.birthDateEt.text.toString().trim(),
                type = apb.typeEt.text.toString().trim(),
                color = apb.colorEt.text.toString().trim(),
                size = apb.sizeEt.text.toString().trim(),
                lastVeterinarianVisit = apb.lastVetVisitEt.text.toString().trim(),
                lastVaccination = apb.lastVaccinationEt.text.toString().trim(),
                lastPetShopVisit = apb.lastPetShopVisitEt.text.toString().trim(),
                veterianPhone = apb.veterianPhoneEt.text.toString().trim(),
                veterianSite = apb.veterianSiteEt.text.toString().trim(),
            )
            Intent().apply {
                putExtra(Constant.PET, pet)
                setResult(RESULT_OK, this)
                finish()
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
            val requiredFields = listOf(nameEt, birthDateEt, typeEt, colorEt, sizeEt, lastVetVisitEt, lastVaccinationEt, lastPetShopVisitEt, veterianPhoneEt, veterianSiteEt)
            var isValid = true
            requiredFields.forEach {
                if (it.text.toString().trim().isEmpty()) {
                    it.error = "Campo obrigatório"
                    isValid = false
                } else {
                    it.error = null
                }
            }
            return isValid
        }
    }
}
