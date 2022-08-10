package com.ejerciciosFravega.ejercicio1.tests


import com.ejerciciosFravega.ejercicio1.EjerciciosFravegaApplication
import com.ejerciciosFravega.ejercicio1.dto.NearStoreDTO
import com.ejerciciosFravega.ejercicio1.entity.Store
import com.ejerciciosFravega.ejercicio1.repository.StoreDAO
import net.minidev.json.JSONObject
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*
import org.springframework.test.context.TestPropertySource
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity

@SpringBootTest(classes = [EjerciciosFravegaApplication::class])
@TestPropertySource(locations = ["classpath:application.properties"])
class UnitTest(
    @Value("\${server.address}")
    val serverAdr: String,
    @Value("\${server.port}")
    val serverPort: String
) {
    @Autowired
    private lateinit var storeRepository: StoreDAO

    private val api = "http://$serverAdr:$serverPort/api/"
    private val restTemplate = RestTemplate()
    private val headers = HttpHeaders();

    //TODO: bad request test quedaron medio feos


    @Test
    fun testGetStoreByIdBasic() {
        val tempStore = Store(address = "test 420", latitude = -34.89791591254772, longitude = -57.980884181073414)
        storeRepository.save(tempStore)
        val res: ResponseEntity<Store> = restTemplate.getForEntity("${api}stores/${tempStore.id}", Store::class)
        assertTrue(res.statusCode == HttpStatus.OK)
        assertTrue(res.body?.id == tempStore.id)
        storeRepository.delete(tempStore)
    }

    @Test
    fun testGetStoreByIdNonExistent() {
        val res: ResponseEntity<Store> = restTemplate.getForEntity("${api}stores/420420420", Store::class)
        assertTrue(res.statusCode == HttpStatus.NO_CONTENT)
    }

    @Test
    fun testGetStoreByIdBadRequest() {
        try {
            val res: ResponseEntity<Store> = restTemplate.getForEntity("${api}stores/-hola", Store::class)
        } catch (e: HttpClientErrorException.BadRequest) {
            println("Correct exception, BadRequest, test should be passed")
        }
    }

    @Test
    fun testAddStoreBasic() {
        headers.contentType = MediaType.APPLICATION_JSON;
        val tempStore = Store(address = "test 420", latitude = -34.89791591254772, longitude = -57.980884181073414)
        val storeJsonObject = JSONObject()
        storeJsonObject["id"] = 1
        storeJsonObject["address"] = tempStore.address
        storeJsonObject["latitude"] = tempStore.latitude
        storeJsonObject["longitude"] = tempStore.longitude
        val request = HttpEntity<String>(storeJsonObject.toString(), headers)
        val res: ResponseEntity<Long> = restTemplate.postForEntity("${api}stores", request, Long::class)

        assertTrue(res.statusCode == HttpStatus.OK)
        assertTrue(storeRepository.findById(res.body ?: -1).get().address == tempStore.address)
        storeRepository.delete(tempStore)
    }

    @Test
    fun testAddStoreBadRequest() {
        try {
            headers.contentType = MediaType.APPLICATION_JSON;
            val tempStore = Store(address = "test 420", latitude = -34.89791591254772, longitude = -57.980884181073414)
            val storeJsonObject = JSONObject()
            storeJsonObject["id"] = 1
            storeJsonObject["GIVE_ME"] = tempStore.address
            storeJsonObject["BAD"] = tempStore.latitude
            storeJsonObject["REQUEST_NOW"] = tempStore.longitude
            val request = HttpEntity<String>(storeJsonObject.toString(), headers)
            val res: ResponseEntity<Long> = restTemplate.postForEntity("${api}stores", request, Long::class)
        } catch (e: HttpClientErrorException.BadRequest) {
            println("Correct exception, BadRequest, test should be passed")
        }
    }


    @Test
    fun testNearestStore() {
        val res: ResponseEntity<NearStoreDTO> = restTemplate.getForEntity(
            "${api}stores/nearestStore?latitude=-31.423411261500593&longitude=-64.18420226647363",
            NearStoreDTO::class
        )
        assertTrue((res.body?.distance ?: -1.0) > 0.0)
        assertTrue((res.body?.nearStore?.id ?: -1) > 0)
    }

    @Test
    fun testNearestStoreBadRequest() {
        try {
            val res: ResponseEntity<NearStoreDTO> = restTemplate.getForEntity(
                "${api}stores/nearestStore?latitude=BAD_REQUEST&longitude=GET_IN_MY_BELLY",
                NearStoreDTO::class
            )
        } catch (e: HttpClientErrorException.BadRequest) {
            println("Correct exception, BadRequest, test should be passed")
        }
    }
}