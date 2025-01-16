package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.dao.ErrorLogDao
import jp.ceed.android.mylapslogger.entity.ErrorLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ErrorLogRepositoryTest {

    private val dao: ErrorLogDao = mockk<ErrorLogDao>(relaxed = true) {
        every { findAll() } returns listOf(
            ErrorLog(1,"stackTrace1", 1735857692632L),
            ErrorLog(2, "stackTrace2", 1735857692632L),
            ErrorLog(3, "stackTrace3", 1735857692632L),
            ErrorLog(4, "stackTrace4", 1735888000000L),
        )
    }

    private val repository = ErrorLogRepository(dao, Dispatchers.Main)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun findAll() {
        runBlocking {
            val errorLogItemList = repository.findAll()
            Truth.assertThat(errorLogItemList.size).isEqualTo(4)
            Truth.assertThat(errorLogItemList.first().stackTrace).isEqualTo("stackTrace1")
            Truth.assertThat(errorLogItemList.first().dateTime).isEqualTo("2025-01-03 07:41:32")
            Truth.assertThat(errorLogItemList.last().stackTrace).isEqualTo("stackTrace4")
            Truth.assertThat(errorLogItemList.last().dateTime).isEqualTo("2025-01-03 16:06:40")
        }

    }

    @Test
    fun insert() {
        runBlocking {
            repository.insert(ErrorLog(1,"stackTrace1", 1735857692632L))
            coVerify {
                dao.insert(ErrorLog(1,"stackTrace1", 1735857692632L))
            }
        }
    }
}