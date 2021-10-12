package com.vhenri.android.tasklistapp.ui

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
open class BaseTest : AutoCloseKoinTest() {
    val dispatcher = TestCoroutineDispatcher()

    @MockK(relaxed = true)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    init {
        val contextMock = mockk<Context>(relaxed = true)
        stopKoin() // If already started
        startKoin {
            androidContext(contextMock)
            modules(module {
                single { TaskViewModel() }
            })
        }
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }
}