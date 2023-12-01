package com.example.mydearpetapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mydearpetapp.model.Contacto
import com.example.mydearpetapp.model.Pendiente
import com.example.mydearpetapp.model.Pet

@Dao
interface PetDao {
    @Insert
    fun insert(pet: Pet):Long
    @Update
    fun update(pet: Pet)
    @Delete
    fun delete(pet: Pet)
    @Query("select * from tblPets order by id")
    fun getPets():LiveData<List<Pet>>
    @Query("select * from tblPets order by id")
    fun getPetsList():List<Pet>
    @Query("select * from tblPets where id=:idInput")
    fun getPetById(idInput:Int):List<Pet>
}
@Dao
interface ContactoDao {
    @Insert
    fun insert(contacto: Contacto):Long
    @Update
    fun update(contacto: Contacto)
    @Delete
    fun delete(contacto: Contacto)
    @Query("select * from tblContactos order by id")
    fun getContactos():LiveData<List<Contacto>>
    @Query("select * from tblContactos where id=:idInput")
    fun getContactoById(idInput:Int):List<Contacto>
}
@Dao
interface PendienteDao {
    @Insert
    fun insert(pendiente: Pendiente):Long
    @Delete
    fun delete(pendiente: Pendiente)
    @Query("select * from tblPendientesCompra where id=:idInput")
    fun getPendienteById(idInput:Int):List<Pendiente>
   @Query("select * from tblPendientesCompra order by id")
    fun getPendientes():LiveData<List<Pendiente>>
}