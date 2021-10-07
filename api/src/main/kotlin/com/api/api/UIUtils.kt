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
    val body = when(responseType) {
        "success" -> if(isNull(responseBody)) "Operação realizada com sucesso." else responseBody
        "bad_request" -> if(isNull(responseBody)) "Parâmetros incorretos. Tente novamente." else responseBody
        "unauthorized" -> if(isNull(responseBody)) "Não autorizado. Tente novamente." else responseBody
        "not_found" -> if(isNull(responseBody)) "Não foram encontrados resultados." else responseBody
        else -> if(isNull(responseBody)) "Não foi possível executar a operação." else responseBody
    }

    return when(responseType) {
        "success" -> Response.status(Response.Status.OK).entity(body).build()
        "bad_request" -> Response.status(Response.Status.BAD_REQUEST).entity(body).build()
        "unauthorized" -> Response.status(Response.Status.UNAUTHORIZED).entity(body).build()
        "not_found" -> Response.status(Response.Status.NOT_FOUND).entity(body).build()
        else -> Response.status(Response.Status.BAD_REQUEST).entity(body).build()
    }
}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}