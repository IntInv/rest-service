package com.intinv.msgs;

data class PriceResponse(
	val prices: Map<String, Double>
): Response()
