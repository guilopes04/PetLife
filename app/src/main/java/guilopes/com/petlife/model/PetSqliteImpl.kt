package guilopes.com.petlife.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class PetSqliteImpl(
    context: Context,
) : PetDao {
    companion object {
        private const val PET_DATABASE_FILE = "pet"
        private const val PET_TABLE = "pet"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val BIRTHDATE_COLUMN = "birth_date"
        private const val TYPE_COLUMN = "type"
        private const val COLOR_COLUMN = "color"
        private const val SIZE_COLUMN = "size"
        private const val LAST_VET_VISIT_COLUMN = "last_veterinarian_visit"
        private const val LAST_VACCINATION_COLUMN = "last_vaccination"
        private const val LAST_PETSHOP_VISIT_COLUMN = "last_petshop_visit"
        private const val VETERIAN_PHONE_COLUMN = "veterian_phone"
        private const val VETERIAN_SITE_COLUMN = "veterian_site"


        private const val PET_EVENTS_TABLE = "pet_events"
        private const val DATE_COLUMN = "date"
        private const val HOUR_COLUMN = "hour"
        private const val PET_ID_COLUMN = "pet_id"

        private const val CREATE_PET_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $PET_TABLE (" +
                "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "$NAME_COLUMN TEXT NOT NULL, " +
                "$BIRTHDATE_COLUMN TEXT NOT NULL, " +
                "$TYPE_COLUMN TEXT NOT NULL, " +
                "$COLOR_COLUMN TEXT NOT NULL, " +
                "$SIZE_COLUMN TEXT NOT NULL, " +
                "$LAST_VET_VISIT_COLUMN TEXT NOT NULL, " +
                "$LAST_VACCINATION_COLUMN TEXT NOT NULL, " +
                "$LAST_PETSHOP_VISIT_COLUMN TEXT NOT NULL, " +
                "$VETERIAN_PHONE_COLUMN TEXT NOT NULL, " +
                "$VETERIAN_SITE_COLUMN TEXT NOT NULL);"

        private const val CREATE_PET_EVENTS_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $PET_EVENTS_TABLE (" +
                    "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "$PET_ID_COLUMN INTEGER NOT NULL, " +
                    "$DATE_COLUMN TEXT NOT NULL, " +
                    "$HOUR_COLUMN TEXT, " +
                    "$TYPE_COLUMN TEXT NOT NULL);"
    }

    private val petDatabase: SQLiteDatabase =
        context.openOrCreateDatabase(
            PET_DATABASE_FILE,
            Context.MODE_PRIVATE,
            null,
        )

    init {
        try {
            petDatabase.execSQL(CREATE_PET_TABLE_STATEMENT)
            petDatabase.execSQL(CREATE_PET_EVENTS_TABLE_STATEMENT)
        } catch (se: SQLException) {
        }
    }

    override fun createPet(pet: Pet): Long = petDatabase.insert(PET_TABLE, null, petToContentValues(pet))

    override fun retrievePet(id: Long): Pet {
        val cursor =
            petDatabase.query(
                true,
                PET_TABLE,
                null,
                "$ID_COLUMN = ?",
                arrayOf(id.toString()),
                null,
                null,
                null,
                null,
            )
        return if (cursor.moveToFirst()) {
            cursorToPet(cursor)
        } else {
            Pet(
                name = "",
                birthDate = "",
                type = "",
                color = "",
                size = "",
                lastVeterinarianVisit = "",
                lastVaccination = "",
                lastPetShopVisit = "",
                veterianPhone = "",
                veterianSite = "",
            )
        }
    }

    override fun createPetEvent(petEvent: PetEvents): Long = petDatabase.insert(PET_EVENTS_TABLE, null, petEventToContentValues(petEvent))

    override fun retrievePetEvents(id: Long): MutableList<PetEvents> {
        val petEvents = mutableListOf<PetEvents>()
        val cursor = petDatabase.rawQuery("SELECT * FROM ${PET_EVENTS_TABLE}", null)
        while (cursor.moveToNext()) {
            petEvents.add(cursorToPetEvents(cursor))
        }
        return petEvents
    }

    override fun retrievePets(): MutableList<Pet> {
        val petList = mutableListOf<Pet>()
        val cursor = petDatabase.rawQuery("SELECT * FROM $PET_TABLE", null)
        while (cursor.moveToNext()) {
            petList.add(cursorToPet(cursor))
        }
        return petList
    }

    override fun updatePet(pet: Pet): Int =
        petDatabase.update(
            PET_TABLE,
            petToContentValues(pet),
            "$ID_COLUMN = ?",
            arrayOf(pet.id.toString()),
        )

    override fun deletePet(id: Long): Int =
        petDatabase.delete(
            PET_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString()),
        )

    private fun petToContentValues(pet: Pet) =
        ContentValues().apply {
            put(NAME_COLUMN, pet.name)
            put(BIRTHDATE_COLUMN, pet.birthDate)
            put(TYPE_COLUMN, pet.type)
            put(COLOR_COLUMN, pet.color)
            put(SIZE_COLUMN, pet.size)
            put(LAST_VET_VISIT_COLUMN, pet.lastVeterinarianVisit)
            put(LAST_VACCINATION_COLUMN, pet.lastVaccination)
            put(LAST_PETSHOP_VISIT_COLUMN, pet.lastPetShopVisit)
            put(VETERIAN_PHONE_COLUMN, pet.veterianPhone)
            put(VETERIAN_SITE_COLUMN, pet.veterianSite)
        }

    private fun petEventToContentValues(petEvent: PetEvents) =
        ContentValues().apply {
            put(TYPE_COLUMN, petEvent.type)
            put(DATE_COLUMN, petEvent.date)
            put(HOUR_COLUMN, petEvent.hour)
            put(PET_ID_COLUMN, petEvent.pet_id)
        }

    private fun cursorToPet(cursor: Cursor) =
        Pet(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_COLUMN)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN)),
            birthDate = cursor.getString(cursor.getColumnIndexOrThrow(BIRTHDATE_COLUMN)),
            type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COLUMN)),
            color = cursor.getString(cursor.getColumnIndexOrThrow(COLOR_COLUMN)),
            size = cursor.getString(cursor.getColumnIndexOrThrow(SIZE_COLUMN)),
            lastVeterinarianVisit = cursor.getString(cursor.getColumnIndexOrThrow(LAST_VET_VISIT_COLUMN)),
            lastVaccination = cursor.getString(cursor.getColumnIndexOrThrow(LAST_VACCINATION_COLUMN)),
            lastPetShopVisit = cursor.getString(cursor.getColumnIndexOrThrow(LAST_PETSHOP_VISIT_COLUMN)),
            veterianPhone = cursor.getString(cursor.getColumnIndexOrThrow(VETERIAN_PHONE_COLUMN)),
            veterianSite = cursor.getString(cursor.getColumnIndexOrThrow(VETERIAN_SITE_COLUMN)),
        )

    private fun cursorToPetEvents(cursor: Cursor) =
        PetEvents(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_COLUMN)),
            type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COLUMN)),
            date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COLUMN)),
            hour = cursor.getString(cursor.getColumnIndexOrThrow(HOUR_COLUMN)),
            pet_id = cursor.getLong(cursor.getColumnIndexOrThrow(PET_ID_COLUMN))
        )
}
