package com.intinv.domain

data class PortfolioDetail(
	val ticket: String,
	val fullName: String,
	val allocate: Int,
	val profValue: Double,
	val profDrop: Double,
	val userName :String,
	val allocateValue: Double,
	val lotValue: Int,
	val averagePriceValue: Double,
	val invValue: Double,
	val historyTransaction: List<Transaction>
)
