package com.intinv.plugins

import com.intinv.PORTFOLIO_CHANNEL_NAME
import com.intinv.SECURITIES_CHANNEL_NAME
import com.intinv.TRADE_CHANNEL_NAME
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	routing {
		get("/portfolio") {
			// TODO - get portfolio id
			val portfolioResponseString = RedisClientActor.read(PORTFOLIO_CHANNEL_NAME)
			// TODO - generate json and pass it as response
			call.respondText(portfolioResponseString)
		}
		get("/portfolioPosition") {
			// TODO - get portfolio id
			// TODO - get position id
			call.respondText("Position in portfolio response")
		}
		get("/securities") {
			val securitiesListString = RedisClientActor.read(SECURITIES_CHANNEL_NAME)
			// TODO - generate json and pass it as response
			call.respondText("List of securities")
		}
		post("/trade") {
			val responseBody = call.receiveText()
			RedisClientActor.write(TRADE_CHANNEL_NAME, responseBody)
			call.respondText("Trade execution response")
		}
	}
}
