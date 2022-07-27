package jp.ceed.android.mylapslogger.repository


import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.dto.Status
import jp.ceed.android.mylapslogger.loadJsonAsEntity
import jp.ceed.android.mylapslogger.model.SessionListItem
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object ApiRepositoryTest : Spek({

    val sessionResponse: SessionsResponse = loadJsonAsEntity(
        fileName = "session_response.json",
        cls = SessionsResponse::class.java
    )

    val apiRepository = ApiRepository(
        context = mockk<Context>(relaxed = true),
        preferenceDao = mockk(relaxed = true),
        userAccountRepository = mockk(relaxed = true),
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

    describe("ラップリストの生成") {
        context("セッション番号指定の場合") {
            val lapList: List<PracticeResultsItem> = apiRepository.createLapList(sessionResponse, 1)
            it("要素の数が正しい"){
                assertThat(lapList).hasSize(18)
            }
            it("要素の順番が正しい"){
                assertThat(lapList[0]).isInstanceOf(PracticeResultsItem.Section::class.java)
                assertThat(lapList[1]).isInstanceOf(PracticeResultsItem.Lap::class.java)
                assertThat(lapList[17]).isInstanceOf(PracticeResultsItem.Lap::class.java)
            }
            it("セクションが挿入されている") {
                val section: PracticeResultsItem.Section = lapList[0] as PracticeResultsItem.Section
                assertThat(section.sectionTitle).isEqualTo("1")
                assertThat(section.averageDuration).isEqualTo("50.372")
                assertThat(section.medianDuration).isEqualTo("43.546")
            }
            it("LAP要素の内容が正しい") {
                val secondLap: PracticeResultsItem.Lap = lapList[2] as PracticeResultsItem.Lap
                assertThat(secondLap.number).isEqualTo("2")
                assertThat(secondLap.duration).isEqualTo("47.714")
                assertThat(secondLap.status).isEqualTo(Status.SLOWER)
                assertThat(secondLap.diffPrevLap).isEqualTo("2.966")
                assertThat(secondLap.cellBgColor).isEqualTo(R.color.window_back_ground)
                assertThat(secondLap.diffTextColor).isEqualTo(R.color.text_slower)
            }
        }
        context("セッション番号未指定の場合") {
            val lapList: List<PracticeResultsItem> = apiRepository.createLapList(sessionResponse, 0)
            it("要素の数が正しい"){
                assertThat(lapList).hasSize(99)
            }
            it("要素の順番が正しい"){
                assertThat(lapList[0]).isInstanceOf(PracticeResultsItem.Section::class.java)
                assertThat(lapList[1]).isInstanceOf(PracticeResultsItem.Lap::class.java)
                assertThat(lapList[18]).isInstanceOf(PracticeResultsItem.Section::class.java)
            }
            it("セクションが挿入されている") {
                val section: PracticeResultsItem.Section = lapList[18] as PracticeResultsItem.Section
                assertThat(section.sectionTitle).isEqualTo("2")
                assertThat(section.averageDuration).isEqualTo("48.367")
                assertThat(section.medianDuration).isEqualTo("43.210")
            }
            it("LAP要素の内容が正しい") {
                val secondLap: PracticeResultsItem.Lap = lapList[20] as PracticeResultsItem.Lap
                assertThat(secondLap.number).isEqualTo("2")
                assertThat(secondLap.duration).isEqualTo("43.054")
                assertThat(secondLap.status).isEqualTo(Status.FASTER)
                assertThat(secondLap.diffPrevLap).isEqualTo("-0.155")
                assertThat(secondLap.cellBgColor).isEqualTo(R.color.window_back_ground)
                assertThat(secondLap.diffTextColor).isEqualTo(R.color.text_faster)
            }
        }
    }
    describe("セッションデータの生成") {
        val sessionData = apiRepository.createSessionData(sessionResponse)
        it("要素の順番が正しい") {
            assertThat(sessionData[0]).isInstanceOf(PracticeResultsItem.Section::class.java)
            assertThat(sessionData[1]).isInstanceOf(PracticeResultsItem.Summary::class.java)
            assertThat(sessionData[2]).isInstanceOf(PracticeResultsItem.Section::class.java)
            assertThat(sessionData[3]).isInstanceOf(PracticeResultsItem.Summary::class.java)
        }
        it("要素の個数が正しい") {
            assertThat(sessionData).hasSize(12)
        }
        it("LAP要素の内容が正しい") {
            val secondItem: PracticeResultsItem.Summary = sessionData[1] as PracticeResultsItem.Summary
            assertThat(secondItem.number).isEqualTo("12")
            assertThat(secondItem.duration).isEqualTo("42.984")
            assertThat(secondItem.lapCount).isEqualTo(17)
        }
    }
})
