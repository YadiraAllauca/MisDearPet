package com.example.mydearpetapp

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydearpetapp.databinding.ItemNombreMascotaBinding
import com.example.mydearpetapp.databinding.ActivityDatosMascotaBinding
import com.example.mydearpetapp.databinding.ItemContactoBinding
import com.example.mydearpetapp.databinding.ItemPendienteBinding
import com.example.mydearpetapp.model.Contacto
import com.example.mydearpetapp.model.Pendiente
import com.example.mydearpetapp.model.Pet

class PendienteAdapter(var pendientes:List<Pendiente> = emptyList()):RecyclerView.Adapter<PendienteAdapter.PendienteAdapterViewHolder>(){

    lateinit var setOnClickListenerPendienteDelete:(Pendiente) -> Unit
    inner class PendienteAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private var binding:ItemPendienteBinding= ItemPendienteBinding.bind(itemView)
        fun bind(pendiente: Pendiente){
            binding.txtPedienteItem.text = pendiente.contenido
            binding.btnEliminarPendiente.setOnClickListener{
               setOnClickListenerPendienteDelete(pendiente)
            }
        }

    }
    fun updateListPendientes(pendientes:List<Pendiente>){
        this.pendientes = pendientes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendienteAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pendiente, parent, false)
        return PendienteAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendienteAdapterViewHolder, position: Int) {
        val pendiente = pendientes[position]
        holder.bind(pendiente)
    }

    override fun getItemCount(): Int {
        return pendientes.size
    }

}