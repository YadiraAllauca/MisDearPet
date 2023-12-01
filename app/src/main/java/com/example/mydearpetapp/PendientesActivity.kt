package com.example.mydearpetapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.databinding.ActivityContactosBinding
import com.example.mydearpetapp.databinding.ActivityPendientesBinding
import com.example.mydearpetapp.model.Pendiente
import java.util.concurrent.Executors

class PendientesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendientesBinding
    private val appDataBase: AppDataBase by lazy{
        AppDataBase.getInstance(this)
    }
    private val adapter: PendienteAdapter by lazy{
        PendienteAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()
        loadAdapter()
        loadData()
    }
    fun botones(){
        binding.btnHomePen.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddPendiente.setOnClickListener {
            val dialog = DialogPendiente()
            dialog.show(supportFragmentManager, "Dialogo Pendiente")
        }
    }
    fun loadAdapter(){
        val manager = LinearLayoutManager(this)
        binding.listadoPendientes.setLayoutManager(manager)
        binding.listadoPendientes.setHasFixedSize(true)
        binding.listadoPendientes.adapter = adapter
        adapter.setOnClickListenerPendienteDelete={
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Se eliminará el registro")
            builder.setMessage("¿Desea continuar?")
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                Executors.newSingleThreadExecutor().execute {
                    appDataBase.pendienteDao().delete(it)
                    runOnUiThread {
                        Toast.makeText(this,"Registro eliminado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            builder.show()
        }
    }
    fun loadData(){
        appDataBase.pendienteDao().getPendientes().observe(this, {
                pendientes -> adapter.updateListPendientes(pendientes)
        })
    }
}