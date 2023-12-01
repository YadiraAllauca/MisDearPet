package com.example.mydearpetapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.databinding.ActivityDatosMascotaBinding
import com.example.mydearpetapp.model.Pet
import com.example.mydearpetapp.utils.Constants
import java.util.*
import java.util.concurrent.Executors

class DatosMascotaActivity : AppCompatActivity() {
    private var id = 0
    private val appDataBase:AppDataBase by lazy{
        AppDataBase.getInstance(this)
    }
    var nombre: EditText?=null
    var nacimiento: EditText? = null
    var banio: EditText? = null
    var peluqueria: EditText? = null
    var veterinario: EditText? = null
    private lateinit var binding: ActivityDatosMascotaBinding
    private lateinit var pet: Pet
    private lateinit var lista: List<Pet>
    val fechaActual = "10/02/2023"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        eventoGuardar()
        val bundle = intent.extras
        bundle?.let {
            pet = it.getSerializable(Constants.KEY_PET) as Pet
            id = pet.id
            if(pet.tipo.equals("Gato")) binding.opcionGato.isChecked=true
            if(pet.sexo.equals("Hembra")) binding.opcionHembra.isChecked=true
            binding.opcionGato.isEnabled=false
            binding.opcionPerro.isEnabled=false
            binding.opcionMacho.isEnabled=false
            binding.opcionHembra.isEnabled=false
            binding.txtNombre.isEnabled=false
            binding.txtNombre.setText(pet.nombre)
            binding.txtNacimiento.setText(pet.nacimiento)
            binding.txtNacimiento.isEnabled=false
            binding.txtBano.setText(pet.banio)
            binding.txtVeterinaria.setText(pet.veterinario)
            binding.txtPeluqueria.setText(pet.peluqueria)
            binding.txtMedicamento.setText(pet.medicamento)
            binding.txtAlergias.setText(pet.alergias)

        }

    }
    fun init(){
        nombre = binding.txtNombre
        nacimiento = binding.txtNacimiento
        banio = binding.txtBano
        peluqueria = binding.txtPeluqueria
        veterinario = binding.txtVeterinaria
        calendario()
        binding.btnBackListado4.setOnClickListener {
            onBackPressed()
        }
        Executors.newSingleThreadExecutor().execute {
        lista = appDataBase.petDao().getPetsList()}

    }
    fun calendario(){
        var dayf:String
        var monthf: String
        nacimiento?.setOnClickListener {
        val Dialogfecha = DatePickerFragment{year, month, day ->
            dayf = "$day"
            monthf = "$month"
            if(dayf.length==1) dayf = "0"+"$day"
            if(monthf.length==1) monthf = "0"+"$month"
            nacimiento?.setText(dayf+"/"+monthf+"/$year")}
            Dialogfecha.show(supportFragmentManager,"DataPicker")
        }
        banio?.setOnClickListener {
            val Dialogfecha = DatePickerFragment{year, month, day ->
                dayf = "$day"
                monthf = "$month"
                if(dayf.length==1) dayf = "0"+"$day"
                if(monthf.length==1) monthf = "0"+"$month"
                banio?.setText(dayf+"/"+monthf+"/$year")}
            Dialogfecha.show(supportFragmentManager,"DataPicker")
        }
        peluqueria?.setOnClickListener {
            val Dialogfecha = DatePickerFragment{year, month, day ->
                dayf = "$day"
                monthf = "$month"
                if(dayf.length==1) dayf = "0"+"$day"
                if(monthf.length==1) monthf = "0"+"$month"
                peluqueria?.setText(dayf+"/"+monthf+"/$year")}
            Dialogfecha.show(supportFragmentManager,"DataPicker")
        }
        veterinario?.setOnClickListener {
            val Dialogfecha = DatePickerFragment{year, month, day ->
                dayf = "$day"
                monthf = "$month"
                if(dayf.length==1) dayf = "0"+"$day"
                if(monthf.length==1) monthf = "0"+"$month"
                veterinario?.setText(dayf+"/"+monthf+"/$year")}
            Dialogfecha.show(supportFragmentManager,"DataPicker")
        }
    }
    fun eventoGuardar(){
        binding.btnGuardarMascota.setOnClickListener {
           if(binding.txtNombre.text.trim().isNotEmpty() && binding.txtNacimiento.text.isNotEmpty()){
               val tipo = if(binding.opcionPerro.isChecked) binding.opcionPerro.text.toString()
               else binding.opcionGato.text.toString()
               val sexo = if(binding.opcionMacho.isChecked) binding.opcionMacho.text.toString()
               else binding.opcionHembra.text.toString()
               val nombre = binding.txtNombre.text.toString().toUpperCase()
               var nacimiento = binding.txtNacimiento.text.toString()
               if(nacimiento.isEmpty()){ nacimiento = fechaActual}
               var banio = binding.txtBano.text.toString()
               if(banio.isEmpty()){ banio = fechaActual}
               var peluqueria = binding.txtPeluqueria.text.toString()
               if(peluqueria.isEmpty()){ peluqueria = fechaActual}
               var veterinario = binding.txtVeterinaria.text.toString()
               if(veterinario.isEmpty()){ veterinario = fechaActual}
               var medicamento = binding.txtMedicamento.text.toString()
               if(medicamento.isEmpty()){ medicamento = "Ninguno"}
               var alergias = binding.txtAlergias.text.toString()
               if(alergias.isEmpty()){ alergias = "Ninguna"}
               if(id==0){
                   if(aprobarNombre(nombre)){
                          add(Pet(0, tipo, sexo, nombre, nacimiento, banio, peluqueria, veterinario, medicamento, alergias))
                       }else{
                       Toast.makeText(this, "Ya existe el nombre registrado", Toast.LENGTH_LONG)
                           .show()
                   }
               }else{
                   pet = Pet(id, tipo, sexo, nombre, nacimiento, banio, peluqueria, veterinario, medicamento, alergias)
                   edit(pet)
                   val bundle = Bundle().apply {
                       putSerializable(com.example.mydearpetapp.utils.Constants.KEY_PET,pet)
                   }
                   val intent = Intent(this,DetalleMascotaActivity::class.java).apply {
                       putExtras(bundle)
                   }
                   startActivity(intent)

               }
        }else{
              verificarCampos()
           }
    }

    }
    fun verificarCampos(){
        if(binding.txtNombre.text.trim().isEmpty()&& binding.txtNacimiento.text.isEmpty()){
            Toast.makeText(this, "Completar campos Nombre y Fecha de Nacimiento", Toast.LENGTH_LONG)
                .show()
        }else if(binding.txtNombre.text.trim().isEmpty()&& binding.txtNacimiento.text.isNotEmpty()){
            Toast.makeText(this, "Completar campo Nombre", Toast.LENGTH_LONG)
                .show()
        }else{
            Toast.makeText(this, "Completar campo Fecha de Nacimiento", Toast.LENGTH_LONG)
                .show()
        }

    }

    fun add(pet:Pet){
        Executors.newSingleThreadExecutor().execute {
            appDataBase.petDao().insert(pet)
            runOnUiThread{
                Toast.makeText(this, "Mascota registrada", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
    fun aprobarNombre(nombre: String): Boolean{
        for(item: Pet in lista){
            if(nombre.equals(item.nombre)){
                return false;
            }
        }
        return true;
    }
    fun edit(pet: Pet){
        Executors.newSingleThreadExecutor().execute {
            appDataBase.petDao().update(pet)
            runOnUiThread {
                Toast.makeText(this,"Datos de mascota actualizados",Toast.LENGTH_SHORT).show()
            }
        }
    }
    class DatePickerFragment(val listener: (year:Int, month:Int, day:Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(),this, year, month, day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year,month+1,day)
        }

    }

}