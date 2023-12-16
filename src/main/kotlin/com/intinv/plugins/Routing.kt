package com.intinv.plugins

import com.google.gson.Gson
import com.intinv.*
import com.intinv.domain.Portfolio
import com.intinv.domain.PortfolioDetail
import com.intinv.domain.Ticket
import com.intinv.domain.Transaction
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	routing {
		get("/portfolio") {
			val portfolioResponseString = RedisClientActor.read(PORTFOLIO_CHANNEL_NAME)
			println(portfolioResponseString)
			// TODO - take json from redis
			val portfolioToRespond = Portfolio(
				fullValue = 432539.12,
				fullProfLossValue = 1000.0,
				captioValue = 2000.0,
				openValue = 3000.0,
				listTicket = listOf(
					Ticket(
						name = "Brabiks",
						fullName = "Barbie and Oppenheimer",
						time = System.currentTimeMillis(),
						price = 176.8,
						changeDay = 12.67
					),
					Ticket(
						name = "Kek",
						fullName = "Kekovna Rekser",
						time = System.currentTimeMillis(),
						price = 6.12,
						changeDay = 0.56
					),
					Ticket(
						name = "Kok",
						fullName = "Stock default",
						time = System.currentTimeMillis(),
						price = 456.1,
						changeDay = 192.67
					),
				)
			)
			call.respond(portfolioToRespond)
		}
		get("/portfolio/{label}") {
			val label = call.parameters["label"]
			if (label != null) {

				// firstly ask for publishing position detail in channel, then read it ????
				RedisClientActor.write(PORTFOLIO_POSITION_ASK_CHANNEL_NAME, label)
				val portfolioDetailFromRedis = RedisClientActor.read(PORTFOLIO_POSITION_CHANNEL_NAME)
				println(portfolioDetailFromRedis)

				// TODO - get position by label from redis

				val detailToRespond = PortfolioDetail(
					ticket = label,
					fullName = "Stub full name",
					allocate = 1,
					profValue = 348.09,
					profDrop = 34.1,
					userName = "Foo Bar",
					allocateValue = 45678.2,
					lotValue = 65,
					averagePriceValue = 34.45,
					invValue = 13232.2,
					historyTransaction = listOf(
						Transaction(
							type = "Kek",
							name = label,
							quantity = 42,
							dateTransaction = System.currentTimeMillis(),
							price = 34.98,
						),
						Transaction(
							type = "Drop",
							name = label,
							quantity = 4,
							dateTransaction = System.currentTimeMillis(),
							price = 12.2,
						),
						Transaction(
							type = "rm -rf /*",
							name = label,
							quantity = 90,
							dateTransaction = System.currentTimeMillis(),
							price = 43.2,
						),
						Transaction(
							type = "Bar",
							name = label,
							quantity = 1000,
							dateTransaction = System.currentTimeMillis(),
							price = 330.0,
						)
					),
				)
				call.respond(detailToRespond)
			} else {
				call.respond(HttpStatusCode.BadRequest, "Тег позиции не определён в запросе.")
			}
		}
		get("/securities") {
			val securitiesListString = RedisClientActor.read(SECURITIES_CHANNEL_NAME)
			println(securitiesListString)

			// TODO - take json from redis, parse it to body and pass as response

			val securitiesListToRespond = listOf(
				Ticket(
					name = "Brabiks",
					fullName = "Barbie and Oppenheimer",
					time = System.currentTimeMillis(),
					price = 176.8,
					changeDay = 12.67
				),
				Ticket(
					name = "Kek",
					fullName = "Kekovna Rekser",
					time = System.currentTimeMillis(),
					price = 6.12,
					changeDay = 0.56
				),
				Ticket(
					name = "Kok",
					fullName = "Stock default",
					time = System.currentTimeMillis(),
					price = 456.1,
					changeDay = 192.67
				),
				Ticket(
					name = "Brabiks",
					fullName = "Barbie and Oppenheimer",
					time = System.currentTimeMillis(),
					price = 176.8,
					changeDay = 12.67
				),
				Ticket(
					name = "Kek",
					fullName = "Kekovna Rekser",
					time = System.currentTimeMillis(),
					price = 6.12,
					changeDay = 0.56
				),
				Ticket(
					name = "Kok",
					fullName = "Stock default",
					time = System.currentTimeMillis(),
					price = 456.1,
					changeDay = 192.67
				)
			)
			call.respond(securitiesListToRespond)
		}
		post("/trade") {
			val responseBody = call.receive<Transaction>().copy(
				type = "Send to execute"
			)

			val responseBodyJson = Gson().toJson(responseBody)
			RedisClientActor.write(TRADE_CHANNEL_NAME, responseBodyJson)

			call.respond(responseBody)
		}
	}
}
