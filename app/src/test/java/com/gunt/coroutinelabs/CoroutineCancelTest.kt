package com.gunt.coroutinelabs

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class CoroutineCancelTest {

    @Test
    fun doCancelTest() = runBlocking {
        cancelJob()
    }

    private suspend fun cancelJob() = coroutineScope {
        val job1 = launch {
            withContext(NonCancellable) {
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            }
            delay(1000L)
            print("job1: end")
        }

        val job2 = launch {
            try {
                println("launch2: ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            } finally {
                println("job2 is finishing!")
            }
        }

        val job3 = launch {
            try {
                println("launch3: ${Thread.currentThread().name}")
                delay(1000L)
                println("2!")
            } finally {
                println("job3 is finishing!")
            }
        }

        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        println("4!")
    }

    private suspend fun getRandom1(): Int {
        delay(1000L)
        println("1!")
        return Random.nextInt(0, 500)
    }

    private suspend fun getRandom2(): Int {
        delay(500L)
        println("2!")
        throw IllegalStateException()
    }

    @Test
    fun cancelChildCoroutineTest() = runBlocking {
        // 로그 순서: 3! -> 4! -> 2! -> Error 발생
        // start 호출 후 parent coroutine 계속 동작 수행 -> 3! -> 4!
        // getRandom2()에서 Error 발생 -> parent coroutine도 취소됨
        // 따라서 getRandom1()은 실행되지 않음
        val elapsedTime = measureTimeMillis {
            val value1 = async(start = CoroutineStart.LAZY) { getRandom1() }
            val value2 = async(start = CoroutineStart.LAZY) { getRandom2() }

            println("3!")
            value1.start()
            value2.start()
            println("4!")

            println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
        }
        println(elapsedTime)
    }
}