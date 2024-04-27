package jp.ceed.android.mylapslogger.util


import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.dto.Status
import jp.ceed.android.mylapslogger.loadJsonAsEntity
import jp.ceed.android.mylapslogger.network.response.Sessions
import org.junit.jupiter.api.Test

class SessionDataCreatorTest {

    private val sessionResponse: Sessions = loadJsonAsEntity(
        fileName = "session_response.json",
        cls = Sessions::class.java
    )

    private val sessionDataCreator = SessionDataCreator(
        context = mockk(relaxed = true)
    )

    @Test
    fun createLapList() {
        // セッション番号指定の場合
        var lapList: List<PracticeResultsItem> = sessionDataCreator.createLapList(sessionResponse, 1)

        // 要素の数が正しい
        assertThat(lapList).hasSize(18)

        // 要素の順番が正しい
        assertThat(lapList[0]).isInstanceOf(PracticeResultsItem.Section::class.java)
        assertThat(lapList[1]).isInstanceOf(PracticeResultsItem.Lap::class.java)
        assertThat(lapList[17]).isInstanceOf(PracticeResultsItem.Lap::class.java)

        // セクションが挿入されている
        var section: PracticeResultsItem.Section = lapList[0] as PracticeResultsItem.Section
        assertThat(section.sectionTitle).isEqualTo("1")
        assertThat(section.averageDuration).isEqualTo("50.372")
        assertThat(section.medianDuration).isEqualTo("43.546")

        // LAP要素の内容が正しい
        var secondLap: PracticeResultsItem.Lap = lapList[2] as PracticeResultsItem.Lap
        assertThat(secondLap.number).isEqualTo("2")
        assertThat(secondLap.duration).isEqualTo("47.714")
        assertThat(secondLap.status).isEqualTo(Status.SLOWER)
        assertThat(secondLap.diffPrevLap).isEqualTo("2.966")
        assertThat(secondLap.cellBgColor).isEqualTo(R.color.window_back_ground)
        assertThat(secondLap.diffTextColor).isEqualTo(R.color.text_slower)

        // セッション番号未指定の場合
        lapList = sessionDataCreator.createLapList(sessionResponse, 0)

        // 要素の数が正しい
        assertThat(lapList).hasSize(99)

        // セクションが挿入されている
        section = lapList[18] as PracticeResultsItem.Section
        assertThat(section.sectionTitle).isEqualTo("2")
        assertThat(section.averageDuration).isEqualTo("48.367")
        assertThat(section.medianDuration).isEqualTo("43.210")

        // LAP要素の内容が正しい
        secondLap = lapList[20] as PracticeResultsItem.Lap
        assertThat(secondLap.number).isEqualTo("2")
        assertThat(secondLap.duration).isEqualTo("43.054")
        assertThat(secondLap.status).isEqualTo(Status.FASTER)
        assertThat(secondLap.diffPrevLap).isEqualTo("-0.155")
        assertThat(secondLap.cellBgColor).isEqualTo(R.color.window_back_ground)
        assertThat(secondLap.diffTextColor).isEqualTo(R.color.text_faster)
    }

    @Test
    fun createSessionData() {
        val sessionData = sessionDataCreator.createSessionData(sessionResponse)

        // 要素の順番が正しい
        assertThat(sessionData[0]).isInstanceOf(PracticeResultsItem.Section::class.java)
        assertThat(sessionData[1]).isInstanceOf(PracticeResultsItem.Summary::class.java)
        assertThat(sessionData[2]).isInstanceOf(PracticeResultsItem.Section::class.java)
        assertThat(sessionData[3]).isInstanceOf(PracticeResultsItem.Summary::class.java)

        // 要素の個数が正しい
        assertThat(sessionData).hasSize(12)

        // LAP要素の内容が正しい
        val secondItem: PracticeResultsItem.Summary = sessionData[1] as PracticeResultsItem.Summary
        assertThat(secondItem.number).isEqualTo("12")
        assertThat(secondItem.duration).isEqualTo("42.984")
        assertThat(secondItem.lapCount).isEqualTo(17)
    }
}
