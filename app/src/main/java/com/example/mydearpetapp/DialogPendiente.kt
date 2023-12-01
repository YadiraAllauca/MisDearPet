package com.example.mydearpetapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mydearpetapp.R.*
import com.example.mydearpetapp.database.AppDataBase
import com.example.mydearpetapp.model.Pendiente
import com.example.mydearpetapp.model.Pet
import java.util.concurrent.Executors

class DialogPendiente: DialogFragment() {
    private lateinit var button: ImageView
    private val appDataBase: AppDataBase by lazy{
        AppDataBase.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.dialog_pendiente, container, false)
        button = view.findViewById(R.id.btnGuardarPendiente)
        button.setOnClickListener {
            val contenido = view.findViewById<EditText>(R.id.txtPendienteDialogo).text.toString()
            if(contenido.isNotEmpty()) {
                add(Pendiente(0, contenido))
                dismiss()
            }else{
                Toast.makeText(requireContext(),"Ingrese un pendiente", Toast.LENGTH_SHORT).show()
                view.findViewById<EditText>(R.id.txtPendienteDialogo).requestFocus()
            }
        }
        return view
    }
    fun add(pendiente: Pendiente){
        Executors.newSingleThreadExecutor().execute {
            appDataBase.pendienteDao().insert(pendiente)
        }
        Toast.makeText(requireContext(),"Agregado", Toast.LENGTH_SHORT).show()
    }

}