package guilopes.com.petlife.controller

import guilopes.com.petlife.model.Pet
import guilopes.com.petlife.model.PetDao
import guilopes.com.petlife.model.PetSqliteImpl
import guilopes.com.petlife.ui.MainActivity

class MainController(
    mainActivity: MainActivity,
) {
    private val petDao: PetDao = PetSqliteImpl(mainActivity)

    fun insertPet(pet: Pet) = petDao.createPet(pet)

    fun getPet(id: Long) = petDao.retrievePet(id)

    fun getPets() = petDao.retrievePets()

    fun modifyPet(pet: Pet) = petDao.updatePet(pet)

    fun removePet(id: Long) = petDao.deletePet(id)
}
