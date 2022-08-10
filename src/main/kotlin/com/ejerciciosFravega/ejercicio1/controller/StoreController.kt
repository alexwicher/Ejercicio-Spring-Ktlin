package com.ejerciciosFravega.ejercicio1.controller

import com.ejerciciosFravega.ejercicio1.dto.NearStoreDTO
import com.ejerciciosFravega.ejercicio1.entity.Store
import com.ejerciciosFravega.ejercicio1.repository.StoreDAO
import com.ejerciciosFravega.ejercicio1.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class StoreController(private val storeRepository: StoreDAO, private val storeService: StoreService) {

    @PostMapping("/stores")
    fun addStore(@Valid @RequestBody store: Store): ResponseEntity<Long> {
        return ResponseEntity.ok(storeRepository.save(store).id)
    }

    @GetMapping("/stores/{id}")
    fun getStore(@PathVariable(value = "id") storeId: Long): ResponseEntity<Store> {
        return try {
            ResponseEntity.ok(storeRepository.findById(storeId).get())
        } catch (e: NoSuchElementException) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/stores/all")
    fun getAllStores(): ResponseEntity<MutableList<Store>> {
        return try {
            ResponseEntity.ok(storeRepository.findAll())
        } catch (e: NoSuchElementException) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/stores/nearestStore")
    fun getNearestStore(
        @RequestParam("latitude") lat: Double,
        @RequestParam("longitude") long: Double
    ): ResponseEntity<NearStoreDTO> {
        return try {
            ResponseEntity.ok(storeService.minDistanceStore(lat, long))
        } catch (e: NoSuchElementException) {
            ResponseEntity.noContent().build()
        }
    }
}
