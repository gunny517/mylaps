package jp.ceed.android.mylapslogger.repository

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.loadJsonAsEntity
import jp.ceed.android.mylapslogger.network.response.Activities
import jp.ceed.android.mylapslogger.network.response.Sessions
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApiRepositoryTest {

    private val sessionResponse: Sessions = loadJsonAsEntity(
        fileName = "session_response.json",
        cls = Sessions::class.java
    )

    private val activitiesResponse: Activities = loadJsonAsEntity(
        fileName = "activities_response.json",
        cls = Activities::class.java
    )

    private val activitiesApiDataSource: ActivitiesApiDataSource = mockk(relaxed = true) {
        coEvery {
            getActivities(any())
        } returns activitiesResponse
    }

    private val apiRepository = ApiRepository(
        context = mockk<Context>(relaxed = true),
        activitiesApiDataSource = activitiesApiDataSource,
        sessionsApiDataSource = mockk(),
        sessionDataCreator = mockk(),
    )

    @Test
    fun loadPracticeResultForPracticeTable() {
    }

    @Test
    fun loadPracticeResultsForSessionList() {
    }

    @Test
    fun createSessionItemList() {
        val sessionList = apiRepository.createSessionItemList(sessionResponse)
        assertEquals(6, sessionList.size)
        assertThat(sessionList[0].no).isEqualTo("1")
        assertThat(sessionList[0].bestLap).isEqualTo("42.984")
        assertThat(sessionList[0].lapCount).isEqualTo("17")
        assertThat(sessionList[0].bestLapTextColor).isEqualTo(R.color.text_default)
    }

    @Test
    fun getPracticeResult() {
    }

    @Test
    fun getActivities() {
        runTest {
            val activities = apiRepository.getActivities("")
            assertThat(activities).hasSize(168)
            assertThat(activities[1].id).isEqualTo(1040849142)
            assertThat(activities[1].locationId).isEqualTo(3712)
            assertThat(activities[1].startTime).isEqualTo("2022-06-26T09:15:54+09:00")
            assertThat(activities[1].endTimeRaw).isEqualTo("2022-06-26T16:05:09+09:00")
            assertThat(activities[1].place).isEqualTo("オートパラダイス御殿場小山町大御神サーキット")
            assertThat(activities[1].trackLength).isEqualTo(1003)
        }
    }
}