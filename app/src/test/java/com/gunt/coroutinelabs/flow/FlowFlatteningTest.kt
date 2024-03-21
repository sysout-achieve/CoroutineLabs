package com.gunt.coroutinelabs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowFlatteningTest {

    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // wait 500 ms
        emit("$i: Second")
    }

    @Test
    fun flatMapConcatTest() = runBlocking {
        // flatMapConcat은 첫번째 요소에 대해서 플레트닝 후 두번째 요소 방출
        // print:
        // 1: First at 128 ms from start
        // 1: Second at 629 ms from start
        // 2: First at 729 ms from start
        // 2: Second at 1229 ms from start
        // 3: First at 1330 ms from start
        // 3: Second at 1830 ms from start
        val startTime = System.currentTimeMillis() // remember the start time
        println("startTime : ${System.currentTimeMillis()}")
        (1..3).asFlow().onEach {
            delay(100)
        } // a number every 100 ms
            .flatMapConcat {
                requestFlow(it)
            }
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }

    @Test
    fun flatMapMergeTest() = runBlocking {
        // flatMapMerge는 첫 요소의 플레트닝을 시작, 다음 요소의 플레트닝을 시작
        // print:
        // 1: First at 168 ms from start
        // 2: First at 264 ms from start
        // 3: First at 366 ms from start
        // 1: Second at 668 ms from start
        // 2: Second at 765 ms from start
        // 3: Second at 867 ms from start
        val startTime = System.currentTimeMillis() // remember the start time
        println("startTime : ${System.currentTimeMillis()}")
        (1..3).asFlow().onEach {
            delay(100)
        } // a number every 100 ms
            .flatMapMerge {
                requestFlow(it)
            }
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
    @Test
    fun flatMapLatestTest() = runBlocking {
        // flatMapMerge는 첫 요소의 플레트닝을 시작, 다음 요소의 플레트닝을 시작
        // print:
        // 1: First at 168 ms from start
        // 2: First at 314 ms from start
        // 3: First at 416 ms from start
        // 3: Second at 916 ms from start
        val startTime = System.currentTimeMillis() // remember the start time
        println("startTime : ${System.currentTimeMillis()}")
        (1..3).asFlow().onEach {
            delay(100)
        } // a number every 100 ms
            .flatMapLatest {
                requestFlow(it)
            }
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
}