package com.example.mydearpetapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.databinding.ActivityContactosBinding
import com.example.mydearpetapp.databinding.ActivityMascotasBinding
import java.util.concurrent.Executors

class ContactosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactosBinding
    private val adapter: ContactoAdapter by lazy{
        ContactoAdapter()
    }
    private val appDataBase: AppDataBase by lazy{
        AppDataBase.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()
        loadAdapter()
        loadData()
    }
    fun botones(){
        binding.btnHomeCon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddContacto.setOnClickListener {
            val intent = Intent(this, DatosContactoActivity::class.java)
            startActivity(intent)
        }
    }
    fun loadAdapter(){
        val manager = LinearLayoutManager(this)
        binding.listadoContactos.setLayoutManager(manager)
        binding.listadoContactos.setHasFixedSize(true)
        binding.listadoContactos.adapter = adapter
        adapter.setOnClickListenerContactoEdit = {
            var bundle  =  Bundle().apply {
                putSerializable(com.example.mydearpetapp.utils.Constants.KEY_CONTACTO,it)
            }
            val intent = Intent(this,DatosContactoActivity::class.java).apply {
                putExtras(bundle)

            }
            startActivity(intent)
        }
        adapter.setOnClickListenerContactoDelete= {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Se eliminará el registro")
            builder.setMessage("¿Desea continuar?")
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                Executors.newSingleThreadExecutor().execute {
                    appDataBase.contactoDao().delete(it)
                    runOnUiThread {
                        Toast.makeText(this,"Registro eliminado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            builder.show()

        }
    }
    fun loadData(){
            appDataBase.contactoDao().getContactos().observe(this, {
                    contactos -> adapter.updateListContactos(contactos)
            })
    }
}