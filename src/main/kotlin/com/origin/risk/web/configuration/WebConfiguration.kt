package com.origin.risk.web.configuration

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.context.support.beans
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.text.SimpleDateFormat

internal val beans = beans {
    bean(isPrimary = true) { jackson2ObjectMapperBuilderSnakeCase() }
}

private fun jackson2ObjectMapperBuilderSnakeCase(): Jackson2ObjectMapperBuilder {
    return Jackson2ObjectMapperBuilder()
        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .dateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"))
}