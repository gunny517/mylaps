package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.network.response.Sessions
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object PracticeResultsRepositoryTest : Spek({

    val sessions: Sessions = mockk {
        every {
            sessions
        } returns listOf( mockk {
            every {
                laps
            } returns listOf(
                mockk {
                    every {
                        dateTimeStart
                    } returns "2022-07-17T16:19:23.009+09:00"
                    every {
                        duration
                    } returns "42.50"
                }
            )
        })
    }

    val practiceResultsRepository = PracticeResultsRepository(
        sessionsApiDataSource = mockk(),
        sessionDataCreator = mockk(),
    )

    describe("次回ローディング時間の計算") {
        it("計算結果が正しいこと") {
            val result = practiceResultsRepository.calculateNextLoadTime(sessions)
            assertThat(result).isEqualTo(1658042364093)
        }
    }
})