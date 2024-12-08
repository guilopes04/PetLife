package guilopes.com.petlife.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityPetBinding
import guilopes.com.petlife.model.Constant
import guilopes.com.petlife.model.Constant.PET
import guilopes.com.petlife.model.Constant.VIEW_MODE
import guilopes.com.petlife.model.Pet

class PetActivity : AppCompatActivity() {
    private val apb: ActivityPetBinding by lazy {
        ActivityPetBinding.inflate(layoutInflater)
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

        apb.saveBt.setOnClickListener {
            val pet = Pet(
                id = receivedPet?.id ?: 0,
                name = apb.nameEt.text.toString(),
                birthDate = apb.birthDateEt.text.toString(),
                type = apb.typeEt.text.toString(),
                color = apb.colorEt.text.toString(),
                size = apb.sizeEt.text.toString(),
                lastVeterinarianVisit = apb.lastVetVisitEt.text.toString(),
                lastVaccination = apb.lastVaccinationEt.text.toString(),
                lastPetShopVisit = apb.lastPetShopVisitEt.text.toString(),
                veterianPhone = apb.veterianPhoneEt.text.toString(),
                veterianSite = apb.veterianSiteEt.text.toString()
            )
            Intent().apply {
                putExtra(Constant.PET, pet)
                setResult(RESULT_OK, this)
                finish()
            }
        }
    }
}
