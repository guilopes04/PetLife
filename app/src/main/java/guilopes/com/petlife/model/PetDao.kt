package guilopes.com.petlife.model

interface PetDao {
    fun createPet(pet: Pet): Long

    fun retrievePet(id: Long): Pet

    fun retrievePets(): MutableList<Pet>

    fun retrievePetEvents(id: Long): MutableList<PetEvents>

    fun createPetEvent(petEvent: PetEvents): Long

    fun updatePet(pet: Pet): Int

    fun deletePet(id: Long): Int
}
