package jp.ceed.android.mylapslogger.repository


import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.loadJsonAsEntity
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.network.response.Activities
import jp.ceed.android.mylapslogger.network.response.Sessions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitPlatform::class)
object ApiRepositoryTest : Spek({

    val sessionResponse: Sessions = loadJsonAsEntity(
        fileName = "session_response.json",
        cls = Sessions::class.java
    )

    val activitiesResponse: Activities = loadJsonAsEntity(
        fileName = "activities_response.json",
        cls = Activities::class.java
    )

    val activitiesApiDataSource: ActivitiesApiDataSource = mockk(relaxed = true) {
        coEvery {
            getActivities(any())
        } returns activitiesResponse
    }

    val apiRepository = ApiRepository(
        context = mockk<Context>(relaxed = true),
        activitiesApiDataSource = activitiesApiDataSource,
        sessionsApiDataSource = mockk(),
        sessionDataCreator = mockk(),
    )

    describe("セッションアイテムリストの生成") {
        val sessionList: List<SessionListItem> = apiRepository.createSessionItemList(sessionResponse)
        it("要素の数が正しい"){
            assertThat(sessionList).hasSize(6)
        }
        it("要素の内容が正しい"){
            assertThat(sessionList[0].no).isEqualTo("1")
            assertThat(sessionList[0].bestLap).isEqualTo("42.984")
            assertThat(sessionList[0].lapCount).isEqualTo("17")
            assertThat(sessionList[0].bestLapTextColor).isEqualTo(R.color.text_default)
        }
    }

    describe("走行日一覧の取得") {
        it("取得した内容が正しい"){
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
})
