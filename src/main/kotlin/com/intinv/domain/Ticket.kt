package com.intinv.domain

data class Ticket(
	val name: String,
	val fullName: String,
	val time: Long,
	val price: Double,
	val changeDay: Double
)
