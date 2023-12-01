package com.example.mydearpetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydearpetapp.databinding.ActivityDetalleMascotaBinding
import com.example.mydearpetapp.model.Pet
import com.example.mydearpetapp.utils.Constants

class DetalleMascotaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleMascotaBinding
    private lateinit var pet: Pet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        bundle?.let {
            pet = it.getSerializable(Constants.KEY_PET) as Pet
            if(pet.tipo.equals("Gato")) binding.imgTipoMascota.setImageResource(R.drawable.cat)
            if(pet.sexo.equals("Hembra")) binding.imgSexoMascota.setImageResource(R.drawable.hembra)
            binding.txtNombreDetalle.text = pet.nombre
            binding.txtNacimientoDetalle.text = pet.nacimiento
            binding.txtBanioDetalle.text = pet.banio
            binding.txtVeterinarioDetalle.text = pet.veterinario
            binding.txtPeluqueriaDetalle.text = pet.peluqueria
            binding.txtMedicamentoDetalle.text = pet.medicamento
            binding.txtAlergiasDetalle.text = pet.alergias

        }
        binding.btnBackListado.setOnClickListener {
            val intent = Intent(this, MascotasActivity::class.java)
            startActivity(intent)
        }
        eventoEditar()
    }
    fun eventoEditar(){
        binding.btnEditarMascota.setOnClickListener {
            val bundle  =  Bundle().apply {
                putSerializable(Constants.KEY_PET,pet)
            }
            val intent = Intent(this, DatosMascotaActivity::class.java).putExtras(bundle)
            startActivity(intent)
        }
    }
}