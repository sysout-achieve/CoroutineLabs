package com.gunt.coroutinelabs.data.remote.repository

import com.gunt.coroutinelabs.data.remote.ApiService
import com.gunt.coroutinelabs.domain.repository.IExampleRepository
import javax.inject.Inject

class ExampleRepo @Inject constructor(
    private val apiService: ApiService
) : IExampleRepository {

    override fun example(): Any {
        TODO("Not yet implemented")
    }

}