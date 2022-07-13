package jp.ceed.android.mylapslogger.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import jp.ceed.android.mylapslogger.entity.Practice
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@DelicateCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PracticeRepositoryTest {

    private lateinit var practiceRepository: PracticeRepository

    @Before
    fun setUp() {
        GlobalScope.launch {
            practiceRepository.deleteAll()
            practiceRepository.savePractice(entity1)
        }
    }

    @After
    fun tearDown() {
        GlobalScope.launch {
            practiceRepository.deleteAll()
        }
    }

    @Test
    fun savePractice() {
        GlobalScope.launch {
            practiceRepository.savePractice(entity2)
            var result = practiceRepository.findById(entity2.id)
            assertThat(result).isEqualTo(entity2)
            practiceRepository.savePractice(entity2_dis_time_null)
            result = practiceRepository.findById(entity2.id)
            assertThat(result).isEqualTo(entity2_dis_time_null)
        }
    }

    @Test
    fun getPracticeIdList() {
        GlobalScope.launch {
            val result = practiceRepository.getPracticeIdList()
            assertThat(result).hasSize(1)
        }
    }

    companion object{
        val entity1 = Practice(
            1,
            111,
            10,
            "42.00",
            "2022-02-05T09:00:58+09:00",
            "2022-02-05T15:00:11+09:00",
            "2017-01-15 (Sun)",
            "5:30:12.492",
            "2:27:31.930"
        )
        val entity2 = Practice(
            2,
            112,
            20,
            "43.00",
            "2022-02-05T09:00:00+09:00",
            "2022-02-05T15:00:00+09:00",
            "2017-01-16 (Mon)",
            "5:30:12.000",
            "2:27:31.000"
        )
        val entity2_dis_time_null = Practice(
            2,
            112,
            20,
            "43.00",
            "2022-02-05T09:00:00+09:00",
            "2022-02-05T15:00:00+09:00",
            null,
            "5:30:12.000",
            "2:27:31.000"
        )
    }
}