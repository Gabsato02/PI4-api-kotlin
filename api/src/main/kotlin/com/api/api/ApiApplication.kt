package com.api.api

import com.fasterxml.jackson.annotation.JsonInclude
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application


@ApplicationPath("/")
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiApplication : Application() {
}