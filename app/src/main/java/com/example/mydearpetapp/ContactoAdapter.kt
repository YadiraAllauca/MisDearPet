package com.example.mydearpetapp

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydearpetapp.databinding.ItemNombreMascotaBinding
import com.example.mydearpetapp.databinding.ActivityDatosMascotaBinding
import com.example.mydearpetapp.databinding.ItemContactoBinding
import com.example.mydearpetapp.model.Contacto
import com.example.mydearpetapp.model.Pet

class ContactoAdapter(var contactos:List<Contacto> = emptyList()):RecyclerView.Adapter<ContactoAdapter.ContactoAdapterViewHolder>(){
    lateinit var setOnClickListenerContactoDelete:(Contacto) -> Unit
    lateinit var setOnClickListenerContactoEdit : (Contacto) -> Unit
    inner class ContactoAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private var binding:ItemContactoBinding= ItemContactoBinding.bind(itemView)
        fun bind(contacto: Contacto){
            binding.txtNombreLocal.text = contacto.local
            binding.txtDireccionLocal.text = contacto.direccion
            binding.txtContactoPersona.text = contacto.persona
            binding.txtTelefono.text = contacto.telefono
            binding.btnEliminarContacto.setOnClickListener{
                setOnClickListenerContactoDelete(contacto)
            }
            binding.btnEditarContacto.setOnClickListener {
                setOnClickListenerContactoEdit(contacto)
            }
        }

    }
    fun updateListContactos(contactos:List<Contacto>){
        this.contactos = contactos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto, parent, false)
        return ContactoAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactoAdapterViewHolder, position: Int) {
        val contacto = contactos[position]
        holder.bind(contacto)
    }

    override fun getItemCount(): Int {
        return contactos.size
    }

}