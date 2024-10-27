package com.example.vpopoo.service

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ApiClient(private val restTemplate: RestTemplate) {

    fun <T> getAll(endpoint: String, page: Int, size: Int, responseType: ParameterizedTypeReference<List<T>>): List<T> {
        val url = "$endpoint?page=$page&size=$size"
        val response: ResponseEntity<List<T>> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            responseType
        )
        return response.body ?: listOf()
    }

    fun <T> get(endpoint: String, responseType: ParameterizedTypeReference<T>): T? {
        val response: ResponseEntity<T> = restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            responseType
        )
        return response.body
    }

    fun <T> gets(endpoint: String, responseType: ParameterizedTypeReference<T>): T? {
        val response: ResponseEntity<T> = restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            responseType
        )
        return response.body
    }

    fun <T> addOrUpdate(endpoint: String, entity: T, responseType: Class<T>): T? {
        return restTemplate.postForObject(endpoint, entity, responseType)
    }

    fun delete(endpoint: String, id: Int, action: String) {
        restTemplate.delete("$endpoint/$id?action=$action")
    }

    fun deleteMultiple(endpoint: String, ids: List<Int>) {
        restTemplate.postForEntity("$endpoint/deleteMultiple", ids, Void::class.java)
    }
}
