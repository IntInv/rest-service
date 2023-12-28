package com.intinv.msgs;




data class InfoResponse (
	var stocks: List<TicketName>
) : Response()

data class TicketName(
	val name: String,
	val fullName: String,
)