package guilopes.com.petlife.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.R
import guilopes.com.petlife.databinding.ActivityAddPetEventBinding
import guilopes.com.petlife.model.Constant.PET
import guilopes.com.petlife.model.Constant.PET_EVENT
import guilopes.com.petlife.model.Pet
import guilopes.com.petlife.model.PetEvents
import java.util.Calendar

class AddPetEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPetEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPetEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val typesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.event_types,
            android.R.layout.simple_spinner_item
        )
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spType.adapter = typesAdapter

        binding.etDate.setOnClickListener {
            showDatePicker()
        }

        binding.etTime.setOnClickListener {
            showTimePicker()
        }

        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                binding.etTime.visibility = if (selectedType == "Medicação") View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.etTime.visibility = View.GONE
            }
        }

        binding.btnSave.setOnClickListener {
            val date = binding.etDate.text.toString()
            val type = binding.spType.selectedItem.toString()
            val time = binding.etTime.text.toString()

            if (date.isEmpty()) {
                Toast.makeText(this, getString(R.string.select_date), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (type == "Medication" && time.isEmpty()) {
                Toast.makeText(this, getString(R.string.select_time), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val petEvent = PetEvents(
                id = 0,
                type = type ?: "",
                date = date ?: "",
                hour = time ?: "",
                pet_id = 1
            )
            Intent().apply {
                putExtra(PET_EVENT, petEvent)
                setResult(RESULT_OK, this)
                finish()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.etDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.etTime.setText(selectedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}
