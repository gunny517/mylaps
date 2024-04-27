package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.network.response.Sessions
import org.junit.jupiter.api.Test

class PracticeResultsRepositoryTest {

    private val sessions: Sessions = mockk {
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

    private val practiceResultsRepository = PracticeResultsRepository(
        sessionsApiDataSource = mockk(),
        sessionDataCreator = mockk(),
    )

    @Test
    fun calculateNextLoadTime() {
        val result = practiceResultsRepository.calculateNextLoadTime(sessions)
        assertThat(result).isEqualTo(1658042364093)
    }
}