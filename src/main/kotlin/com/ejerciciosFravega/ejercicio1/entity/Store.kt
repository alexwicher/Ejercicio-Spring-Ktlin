package com.ejerciciosFravega.ejercicio1.entity


import com.sun.istack.NotNull
import javax.persistence.*

@Entity
@Table(name = "sucursal")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "sucursal_id_seq", sequenceName = "sucursal_id_seq", allocationSize = 1)

    @NotNull
    @Column(name = "id")
    val id: Long = 0,
    @NotNull
    @Column(name = "address")
    val address: String,
    @NotNull
    @Column(name = "latitude")
    val latitude: Double,
    @NotNull
    @Column(name = "longitude")
    val longitude: Double
)
