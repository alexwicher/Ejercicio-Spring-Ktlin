package com.ejerciciosFravega.ejercicio1.service

import com.ejerciciosFravega.ejercicio1.dto.NearStoreDTO
import com.ejerciciosFravega.ejercicio1.entity.Store
import com.ejerciciosFravega.ejercicio1.repository.StoreDAO
import org.springframework.stereotype.Service
import kotlin.math.*

@Service
class StoreService(private val storeRepository: StoreDAO) {

    // TODO: tiene pinta que se puede optimizar el algoritmo
    fun minDistanceStore(lat: Double, lon: Double): NearStoreDTO {
        var nearStore: Store? = null
        var minDist = Double.MAX_VALUE

        storeRepository.findAll().forEach {
            val dist = distanceCoords(it.latitude, it.longitude, lat, lon)
            if (dist < minDist) minDist = dist; nearStore = it
        }
        return NearStoreDTO(nearStore, minDist)
    }

    fun distanceCoords(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        // https://en.wikipedia.org/wiki/Haversine_formula
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val originLat = Math.toRadians(lat1)
        val destinationLat = Math.toRadians(lat2)
        val a =
            sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) * cos(originLat) * cos(destinationLat)
        val c = 2 * asin(sqrt(a))
        return 6371 * c

    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}