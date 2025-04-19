package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.entity.ActivityInfo
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
class ActivityInfoRepositoryTest {

    companion object {
        const val ACTIVITY_ID = 10L
    }

    private val dao = mockk<ActivityInfoDao>(relaxed = true) {
        coEvery { findById(ACTIVITY_ID) } returns mockk<ActivityInfo> {
            every { activityId } returns ACTIVITY_ID
        }
    }

    private val repository: ActivityInfoRepository = ActivityInfoRepository(dao, Dispatchers.Main)

    private val activityInfo = ActivityInfo(2, "", 3.3f, 4, "date_time", "event_name")

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun findById() {
        runBlocking {
            // 返り値の activityId が引数の activityId と同じであること
            Truth.assertThat(repository.findById(ACTIVITY_ID)?.activityId).isEqualTo(ACTIVITY_ID)
        }
    }

    @Test
    fun update() {
        runBlocking {
            repository.update(activityInfo)
            // dao の updateId が実行された事
            coVerify { dao.updateId(activityInfo) }
        }
    }

    @Test
    fun insert() {
        runBlocking {
            repository.insert(activityInfo)
            // dao の insert が実行された事
            coVerify { dao.insert(activityInfo) }
        }
    }
}