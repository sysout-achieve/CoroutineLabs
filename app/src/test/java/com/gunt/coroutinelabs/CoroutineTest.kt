package com.gunt.coroutinelabs

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutineTest {

    @Test
    fun printCoroutineContext() = runBlocking {
        println(coroutineContext)
        println(Thread.currentThread().name)
    }

    @Test
    fun launchTest() = runBlocking {
        // runBlocking이 먼저 호출되고 launch로 만든 블럭을 큐에 담아둔 상태로 동작 후 launch 호출
        launch {
            println("launch: ${Thread.currentThread().name}")
        }
        println("runBlocking: ${Thread.currentThread().name}")
        // runBlocking: main @coroutine#1
        // launch: main @coroutine#2
    }

    @Test
    fun delayTest() {
        runBlocking {   // 계층적, 구조적 -> 코틀린의 경우에 coroutine 부모가 cancel되는 경우 자식도 같이 cancel
            // coroutine은 단일스레드내에서 중단점이 있을 경우에 다른 코드블록을 수행하도록 양보할 수 있음
            launch {
                println("launch1: ${Thread.currentThread().name}")
                delay(600L)
                println("3!")
            }
            launch {
                println("launch2: ${Thread.currentThread().name}")
                println("1!")
            }
            println("runBlocking: ${Thread.currentThread().name}")
            delay(500L)
            println("2!")
        }
        // 4! 는 runBlocking이 끝나고 나서 출력됨
        println("4!")
    }

    @Test
    fun suspendTest() = runBlocking {
        doSuspend()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }

    private suspend fun doSuspend() = coroutineScope {
        launch {
            println("launch1: ${Thread.currentThread().name}")
            delay(600L)
            println("3!")
        }
        launch {
            println("launch2: ${Thread.currentThread().name}")
            println("1!")
        }
        launch {
            println("launch3: ${Thread.currentThread().name}")
            delay(500L)
            println("2!")
        }
        println("4!")
    }


    @Test
    fun suspendCancelTest() = runBlocking {
        cancelJob()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }

    private suspend fun cancelJob() = coroutineScope {
        val job1 = launch {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }

        val job2 = launch {
            println("launch2: ${Thread.currentThread().name}")
            println("1!")
        }

        val job3 = launch {
            println("launch3: ${Thread.currentThread().name}")
            delay(500L)
            println("2!")
        }

        delay(100L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        println("4!")
    }

    suspend fun doOneTwoThree() = coroutineScope {
        val job1 = launch {
            try {
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            } finally {
                println("job1 is finishing!")
            }
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
}
