package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.dao.PracticeDao
import jp.ceed.android.mylapslogger.entity.Practice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PracticeRepositoryTest {

    companion object {
        const val FIRST_ID = 1L
        const val SECOND_ID = 2L
    }

    private val practice1 = Practice(FIRST_ID, 2, 3, "bestLap", "startTime", "endTime", "displayTime", "total", "active")

    private val practice2 = Practice(SECOND_ID, 2, 3, "bestLap", "startTime", "endTime", "displayTime", "total", "active")

    private val dao: PracticeDao = mockk(relaxed = true) {
        every { findByActivityId(FIRST_ID) } returns practice1
        every { findAll() } returns listOf(practice1, practice2)
    }

    private val repository: PracticeRepository = PracticeRepository(dao, Dispatchers.Main)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun findByActivityId() {
        runBlocking {
            repository.findByActivityId(FIRST_ID)
            // dao のメソッドが呼ばれた事
            coVerify { dao.findByActivityId(FIRST_ID) }
        }
    }

    @Test
    fun savePractice() {
        runBlocking {
            repository.savePractice(practice1)
            // dao のメソッドが呼ばれた事
            coVerify { dao.save(practice1) }
        }
    }

    @Test
    fun validPracticeIdList() {
        runBlocking {
            val ids = repository.getPracticeIdList()
            // dao のメソッドが呼ばれた事
            coVerify { dao.findAll() }
            // 戻り値が正しいこと
            Truth.assertThat(ids.size).isEqualTo(2)
            Truth.assertThat(ids.first()).isEqualTo(FIRST_ID)
            Truth.assertThat(ids.last()).isEqualTo(SECOND_ID)
        }
    }
}