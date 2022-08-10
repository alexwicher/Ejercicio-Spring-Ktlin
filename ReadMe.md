# Surcursal distance API (Ejercicio 1)

Para mejorar la experiencia del cliente, la sucursal por defecto que se ofrece para retiro en tienda debe ser la más
cercana a su ubicación, se requiere una funcionalidad para conocer la sucursal más cercana a un punto dado (latitud y
longitud).

+ Tecnologias principales:
    + Spring Boot
    + Kotlin
    + Hybernate
    + PostgreSQL
    + Maven
    + Junit (Para tests)

## Ejecucion y compilacion

+ Tener Intellij:
    + Click derecho en el pom de maven, build module
    + Para el run poner com.ejerciciosFravega.ejercicio1.EjerciciosFravegaApplication como el ejecutable.
+ Terminal:
    + mvn spring-boot:run
    + Usar postman, curl,  intellij, etc. para pegar a la api

## HTTP API REST endpoints ("http:/localhost:8080/api/")

    POST "/stores"

    Inserta una sucursal nueva y retorna el id generado, se el request
    body con el json para insertar:

```json
{
  "address": "25 de mayo",
  "latitude": -34.622882784967246,
  "longitude": -58.38063888135247
}
```

    GET "/stores/{id}"

    Retorna una sucursal correspondiente al id, como en el ejemplo:

```json
{
  "id": 1,
  "address": "25 de mayo",
  "latitude": -34.622882784967246,
  "longitude": -58.38063888135247
}
```

    GET "/stores/all"

    Return lista de todas las sucursales como en el ejemplo.

```json
[
  {
    "id": 1,
    "address": "25 de mayo",
    "latitude": -34.622882784967246,
    "longitude": -58.38063888135247
  },
  {
    "id": 2,
    "address": "Guarida del doctro frio",
    "latitude": -54.64645732097388,
    "longitude": -68.19147298901696
  }
]
```

    GET "/stores/nearestStore&latitude=-31.423411261500593&longitude=-64.18420226647363"

    Retorna distancia en kilometros de la distancia de la sucursal mas cercana 
    y el objeto de la sucursal de acuerdo a los puntos de coordenadas de los parametros.
    Reponde con

```json
{
  "distance": 2000,
  "nearStore": {
    "id": 1,
    "address": "25 de mayo",
    "latitude": -34.622882784967246,
    "longitude": -58.38063888135247
  }
}
```

    











