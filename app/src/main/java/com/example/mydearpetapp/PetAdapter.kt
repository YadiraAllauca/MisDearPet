package com.example.mydearpetapp

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydearpetapp.databinding.ItemNombreMascotaBinding
import com.example.mydearpetapp.databinding.ActivityDatosMascotaBinding
import com.example.mydearpetapp.model.Pet

class PetAdapter(var pets:List<Pet> = emptyList()):RecyclerView.Adapter<PetAdapter.PetAdapterViewHolder>(){
    lateinit var setOnClickPet: (Pet) -> Unit
    lateinit var setOnClickListenerPetDelete:(Pet) -> Unit
    inner class PetAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private var binding:ItemNombreMascotaBinding= ItemNombreMascotaBinding.bind(itemView)
        fun bind(pet: Pet){
            binding.txtNombreMascotaMain.text = pet.nombre
            if(pet.tipo.equals("Gato")) {
                binding.imgNombre.setImageResource(R.drawable.cat)
            }else{
                binding.imgNombre.setImageResource(R.drawable.dog)
            }
            binding.txtNombreMascotaMain.setOnClickListener {
                setOnClickPet(pet)
            }
            binding.btnEliminarMascota.setOnClickListener{
                setOnClickListenerPetDelete(pet)
            }
        }

    }
    fun updateListPets(pets:List<Pet>){
        this.pets = pets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nombre_mascota, parent, false)
        return PetAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetAdapterViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
    }

    override fun getItemCount(): Int {
        return pets.size
    }

}