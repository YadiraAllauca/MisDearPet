package com.example.mydearpetapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydearpetapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botonesPrincipales()
    }
    fun botonesPrincipales(){
        binding.btnMisMascotas.setOnClickListener {
            val intent = Intent(this, MascotasActivity::class.java)
            startActivity(intent)
        }
        binding.btnProfesionales.setOnClickListener {
            val intent = Intent(this, ContactosActivity::class.java)
            startActivity(intent)
        }
        binding.btnCompras.setOnClickListener {
            val intent = Intent(this, PendientesActivity::class.java)
            startActivity(intent)
        }
    }

}