package com.ejerciciosFravega.ejercicio1.repository

import com.ejerciciosFravega.ejercicio1.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreDAO : JpaRepository<Store, Long>