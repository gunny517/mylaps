package jp.ceed.android.mylapslogger.repository


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ApiRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext() as Context

    private val apiRepository = ApiRepository(context)

    @Before
    fun setUp() {
    }


    @Test
    fun loadPracticeResultForPracticeTable() {
        apiRepository.loadPracticeResultForPracticeTable(activitiesItem){
            it.onSuccess { practice ->
                assertThat(practice.bestLap).isEqualTo(TEST_BEST_LAP)
            }.onFailure {
                fail()
            }
        }
    }


    @Test
    fun loadPracticeResultsForSessionList() {
        apiRepository.loadPracticeResultsForSessionList(TEST_ACTIVITY_ID){
            assertThat(it.isSuccess).isTrue()
        }
    }


    @Test
    fun sessionRequest() {
        apiRepository.sessionRequest(TEST_ACTIVITY_ID, 0, 0){
            it.onSuccess { practice ->
                assertThat(practice.bestLap).isEqualTo(TEST_BEST_LAP)
            }.onFailure {
                fail()
            }
        }
    }


    @Test
    fun getActivities() {
        apiRepository.getActivities {
            assertThat(it.isSuccess).isTrue()
        }
    }

    companion object{
        const val TEST_ACTIVITY_ID = 834841864
        const val TEST_BEST_LAP = "43.061"
        val activitiesItem = ActivitiesItem(
            TEST_ACTIVITY_ID,
            0,
            null,
            "",
            "",
            "",
            0)
    }

}