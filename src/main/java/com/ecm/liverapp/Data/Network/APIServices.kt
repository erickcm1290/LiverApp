package com.ecm.liverapp.Data.Network

import com.ecm.liverapp.Data.Models.Models
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIServices {
    @GET
    suspend fun getProductos(@Url url:String): Response<Models.Respuesta>

}