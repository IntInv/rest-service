package com.intinv.plugins

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.newSingleThreadContext
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPubSub

object RedisClientActor {
	private val redisClientCoroutineScope = CoroutineScope(
		newSingleThreadContext("RedisClientThread")
	)
	private val redisActor = redisClientCoroutineScope.actor<RedisAction> {
		val jedisPool = JedisPool("127.0.0.1", 6379)

		jedisPool.resource.use { jedis ->
			for (actorMessage in channel) {
				when (actorMessage) {
					is Read -> {
						val redisSub = object : JedisPubSub() {
							override fun onMessage(channel: String?, message: String?) {
								super.onMessage(channel, message)
								actorMessage.response.complete(
									message ?: "Получена пустая строка по каналу ${actorMessage.channel}."
								)
							}
						}
						jedis.subscribe(redisSub, actorMessage.channel)
					}

					is Write -> {
						jedis.publish(
							actorMessage.channel,
							actorMessage.value
						)
					}
				}
			}
		}
	}

	suspend fun write(channel: String, value: String) {
		redisActor.send(Write(channel, value))
	}

	suspend fun read(channel: String): String {
		val response = CompletableDeferred<String>()
		redisActor.send(Read(channel, response))
		return response.await()
	}

	private sealed class RedisAction
	private class Read(
		val channel: String,
		val response: CompletableDeferred<String>
	) : RedisAction()
	private class Write(
		val channel: String,
		val value: String
	) : RedisAction()
}