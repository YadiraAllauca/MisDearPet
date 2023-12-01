package com.example.mydearpetapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.databinding.ActivityMascotasBinding
import java.util.concurrent.Executors


class MascotasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMascotasBinding
    private val adapter: PetAdapter by lazy{
        PetAdapter()
    }
    private val appDataBase: AppDataBase by lazy{
        AppDataBase.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMascotasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()
        loadAdapter()
        loadData()
    }
    fun botones(){
        binding.btnAddMascota.setOnClickListener {
            val intent = Intent(this, DatosMascotaActivity::class.java)
            startActivity(intent)
        }
        binding.btnHomeMas.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun loadAdapter(){
        val manager = LinearLayoutManager(this)
        binding.listaMascotasNombres.setLayoutManager(manager)
        binding.listaMascotasNombres.setHasFixedSize(true)
        binding.listaMascotasNombres.adapter= adapter
        adapter.setOnClickPet = {
            val bundle = Bundle().apply {
                putSerializable(com.example.mydearpetapp.utils.Constants.KEY_PET,it)
            }
            val intent = Intent(this,DetalleMascotaActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }
        adapter.setOnClickListenerPetDelete = {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Se eliminará el registro")
            builder.setMessage("¿Desea continuar?")
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                Executors.newSingleThreadExecutor().execute {
                    appDataBase.petDao().delete(it)
                    runOnUiThread {
                        Toast.makeText(this,"Registro eliminado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            builder.show()
        }
    }
    fun loadData(){
        appDataBase.petDao().getPets().observe(this, {
                pets -> adapter.updateListPets(pets)
        })
    }
}