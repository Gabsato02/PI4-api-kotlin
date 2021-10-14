package com.api.api

import java.text.SimpleDateFormat
import java.util.*
import java.util.Objects.isNull
import javax.ws.rs.core.Response
import java.math.BigInteger
import java.security.MessageDigest

fun formatDateToTimestamp(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(date)
}

fun returnResponse(responseType: String? = "", responseBody: Any?): Response {
    val customResponse = CustomResponse()

    customResponse.message = when(responseType) {
        "success" -> "Operação realizada com sucesso."
        "bad_request" -> "Parâmetros incorretos. Tente novamente."
        "unauthorized" -> "Não autorizado. Tente novamente."
        "not_found" -> "Não foram encontrados resultados."
        else -> "Não foi possível executar a operação."
    }

    val payload = if(isNull(responseBody)) customResponse else responseBody

    return when(responseType) {
        "success" -> Response.status(Response.Status.OK).entity(payload).build()
        "bad_request" -> Response.status(Response.Status.BAD_REQUEST).entity(payload).build()
        "unauthorized" -> Response.status(Response.Status.UNAUTHORIZED).entity(payload).build()
        "not_found" -> Response.status(Response.Status.NOT_FOUND).entity(payload).build()
        else -> Response.status(Response.Status.BAD_REQUEST).entity(payload).build()
    }
}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}