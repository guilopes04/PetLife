package guilopes.com.petlife

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.content.Intent.EXTRA_INTENT
import android.content.Intent.EXTRA_TITLE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import guilopes.com.petlife.databinding.ActivityEditVeterinarianBinding

class EditVeterinarianActivity : AppCompatActivity() {
    private lateinit var parl: ActivityResultLauncher<Intent>
    private lateinit var pcarl: ActivityResultLauncher<String>
    private lateinit var piarl: ActivityResultLauncher<Intent>

    companion object Constantes {
        const val PARAMETRO_EXTRA = "PARAMETRO_EXTRA"
    }



    private val binding: ActivityEditVeterinarianBinding by lazy {
        ActivityEditVeterinarianBinding.inflate(layoutInflater)
    }



    private fun chamarOuDiscar(chamar: Boolean): View.OnClickListener? {
        Uri.parse("tel: ${binding.etVeterianPhone.text}").let {
            Intent(if (chamar) ACTION_CALL else ACTION_DIAL).apply {
                data = it
                startActivity(this)
            }
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(binding.root)

        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.getStringExtra(PARAMETRO_EXTRA)?.let {
                    binding.btnCall.text = it
                }
            }
        }

        pcarl = registerForActivityResult(ActivityResultContracts.RequestPermission())
        { permissaoConcedida ->
            if (permissaoConcedida) {
                chamarOuDiscar(true)
            }
            else {
                Toast.makeText(this, "Permissão necessária!", Toast.LENGTH_SHORT).show()
            }
        }

        piarl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.data?.let {
                    startActivity(Intent(ACTION_VIEW, it))
                }
            }
        }


        binding.etLastVetVisit.setText(MainActivity.pet.lastVeterinarianVisit)
        binding.etVeterianPhone.setText(MainActivity.pet.veterianPhone)
        binding.etVeterianSite.setText(MainActivity.pet.veterianSite)

        binding.btnCall.setOnClickListener {
            Intent("MINHA_ACTION_PARA_PROXIMA_TELA").apply {
                parl.launch(this)
            }
        }

        binding.btnSave.setOnClickListener {

            MainActivity.pet.lastVeterinarianVisit = binding.etLastVetVisit.text.toString()

            finish()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnCall -> {
                if (checkSelfPermission(CALL_PHONE) == PERMISSION_GRANTED) {
                    chamarOuDiscar(true)
                }
                else {
                    pcarl.launch(CALL_PHONE)
                }
                true
            }
            R.id.btnAccessWebsite -> {
                Uri.parse(binding.etVeterianSite.text.toString()).let { url ->
                    Intent(ACTION_VIEW, url).let { navegadorIntent ->
                        val escolherAppIntent = Intent(ACTION_CHOOSER)
                        escolherAppIntent.putExtra(EXTRA_TITLE, "Escolha seu navegador")
                        escolherAppIntent.putExtra(EXTRA_INTENT, navegadorIntent)
                        startActivity(escolherAppIntent)
                    }
                }
                true
            }
            else -> { false }
        }
    }
}



