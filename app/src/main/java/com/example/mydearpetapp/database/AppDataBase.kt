package com.example.mydearpetapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydearpetapp.model.Contacto
import com.example.mydearpetapp.model.Pendiente
import com.example.mydearpetapp.model.Pet

@Database(entities = [Pet::class,Contacto::class,Pendiente::class], version = 1, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {
    abstract fun petDao():PetDao
    abstract fun contactoDao():ContactoDao
    abstract fun pendienteDao(): PendienteDao
    companion object{
        var instancedb:AppDataBase ?= null
        fun getInstance(context:Context):AppDataBase{
            context.deleteDatabase("dbAppPets");
            context.deleteDatabase("apppets");
           if(instancedb==null){
                instancedb = Room.databaseBuilder(context, AppDataBase::class.java,"appBD").build()
           }
                return instancedb as AppDataBase
        }
    }

}