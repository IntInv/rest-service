package com.intinv.domain

data class Transaction(
	val type: String,
	val name: String,
	val quantity: Int,
	val dateTransaction: Long,
	val price: Double
)
