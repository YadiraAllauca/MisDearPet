package com.example.mydearpetapp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "tblPets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "tipo")
    val tipo:String,
    @ColumnInfo(name = "sexo")
    val sexo:String,
    @ColumnInfo(name = "nombre")
    val nombre:String,
    @ColumnInfo(name = "nacimiento")
    val nacimiento:String,
    @ColumnInfo(name = "banio")
    val banio:String,
    @ColumnInfo(name = "peluqueria")
    val peluqueria:String,
    @ColumnInfo(name = "veterinario")
    val veterinario:String,
    @ColumnInfo(name = "medicamento")
    val medicamento:String,
    @ColumnInfo(name = "alergias")
    val alergias:String
):Serializable
@Entity(tableName = "tblPendientesCompra")
data class Pendiente(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "contenido")
    val contenido:String
):Serializable
@Entity(tableName = "tblContactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "local")
    val local:String,
    @ColumnInfo(name = "direccion")
    val direccion:String,
    @ColumnInfo(name = "persona")
    val persona:String,
    @ColumnInfo(name = "telefono")
    val telefono:String
):Serializable

