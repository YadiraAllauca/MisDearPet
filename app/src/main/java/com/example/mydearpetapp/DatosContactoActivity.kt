package com.example.mydearpetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.databinding.ActivityDatosContactoBinding
import com.example.mydearpetapp.model.Contacto
import com.example.mydearpetapp.model.Pet
import com.example.mydearpetapp.utils.Constants
import java.util.concurrent.Executors

class DatosContactoActivity : AppCompatActivity() {
    private var id = 0
    private lateinit var contacto: Contacto
    private lateinit var binding: ActivityDatosContactoBinding
    private val appDataBase: AppDataBase by lazy{
        AppDataBase.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        eventos()
    }
    private fun init(){
        val bundle = intent.extras
        bundle?.let {
            val contacto = bundle.getSerializable(Constants.KEY_CONTACTO) as Contacto
            id= contacto.id
            binding.txtLocalDato.setText(contacto.local)
            binding.txtDireccionDato.setText(contacto.direccion)
            binding.txtPersonaDato.setText(contacto.persona)
            binding.txtTelefonoDato.setText(contacto.telefono)
        }
    }

    fun eventos(){

        binding.btnGuardarContacto.setOnClickListener {
            if(binding.txtLocalDato.text.trim().isNotEmpty()){
                val local = binding.txtLocalDato.text.toString()
                val direccion = binding.txtDireccionDato.text.toString()
                val persona = binding.txtPersonaDato.text.toString()
                val telefono = binding.txtTelefonoDato.text.toString()
                if(id==0) {
                    add(Contacto(0, local, direccion, persona, telefono))
                }else {
                    contacto = Contacto(id, local, direccion, persona, telefono)
                    edit(contacto)
                }
                onBackPressed()
            }else{
                Toast.makeText(this, "Completar campo Nombre del local", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnBackListado2.setOnClickListener {
            val intent = Intent(this, ContactosActivity::class.java)
            startActivity(intent)
        }

    }
    fun add(contacto: Contacto){
        Executors.newSingleThreadExecutor().execute {
            appDataBase.contactoDao().insert(contacto)
            runOnUiThread{
                Toast.makeText(this, "Contacto registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun edit(contacto: Contacto){
        Executors.newSingleThreadExecutor().execute {
            appDataBase.contactoDao().update(contacto)
            runOnUiThread {
                Toast.makeText(this,"Datos de contacto actualizados",Toast.LENGTH_SHORT).show()
            }
        }
    }
}