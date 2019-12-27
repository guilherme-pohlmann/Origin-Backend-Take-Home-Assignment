package com.origin.risk

import com.origin.risk.web.configuration.beans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class RiskApplication

fun main(args: Array<String>) {
	runApplication<RiskApplication>(*args) {
		this.addInitializers(beans)
	}
}
