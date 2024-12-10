package guilopes.com.petlife.controller

import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.model.Pet
import guilopes.com.petlife.model.PetDao
import guilopes.com.petlife.model.PetEvents
import guilopes.com.petlife.model.PetSqliteImpl

class MainController(
    mainActivity: AppCompatActivity,
) {
    private val petDao: PetDao = PetSqliteImpl(mainActivity)

    fun insertPet(pet: Pet) = petDao.createPet(pet)

    fun getPet(id: Long) = petDao.retrievePet(id)

    fun getPetEvents(id: Long) = petDao.retrievePetEvents(id)

    fun insertPetEvent(petEvent: PetEvents) = petDao.createPetEvent(petEvent)

    fun getPets() = petDao.retrievePets()

    fun modifyPet(pet: Pet) = petDao.updatePet(pet)

    fun removePet(id: Long) = petDao.deletePet(id)
}
