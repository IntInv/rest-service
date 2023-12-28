package com.intinv.domain

import com.intinv.intinvapp.domain.PortfolioTicket

data class Portfolio(
	val fullValue: Double,
	val fullProfLossValue: Double,
	val captioValue: Double,
	val openValue: Double,
	val listTicket: List<PortfolioTicket>
)
