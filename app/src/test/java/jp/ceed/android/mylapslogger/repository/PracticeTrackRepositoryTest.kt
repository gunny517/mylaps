package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.dao.PracticeTrackDao
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.entity.Track
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
class PracticeTrackRepositoryTest {

    private val totalDistanceList = listOf(
        TotalDistance(1, "track1", 80, 100, 800, 5),
        TotalDistance(2, "track2", 90, 100, 900, 6),
        TotalDistance(3, "track3", 100, 100, 1000, 7),
        TotalDistance(4, "track4", 110, 100, 1100, 8),
        TotalDistance(5, "track5", 120, 99, 1200, 9)
    )

    private val trackList = listOf(
        Track(1, "track1", 80, 0L),
        Track(2, "track2", 90, 0L),
        Track(3, "track3", 100, 0L),
    )

    private val practiceTrackDao: PracticeTrackDao = mockk<PracticeTrackDao>(relaxed = true) {
        every { getTotalDistance() } returns totalDistanceList
        every { findBestLapByTrackId(1) } returns PracticeTrack(0L, 1, "track1", 11, "bestLap1", "startTime1", "displayTime1", "endTime1", "totalTime1", 101)
        every { findBestLapByTrackId(2) } returns null
        every { findBestLapByTrackId(3) } returns PracticeTrack(0L, 3, "track3", 13, "bestLap3", "startTime3", "displayTime3", "endTime3", "totalTime3", 103)
    }

    private val trackDao: TrackDao = mockk<TrackDao>(relaxed = true) {
        every { findAll() } returns trackList
    }

    private val repository = PracticeTrackRepository(practiceTrackDao, trackDao, Dispatchers.Main)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTotalDistanceList() {
        runBlocking {
            val totalDistanceList = repository.getTotalDistanceList("label")
            Truth.assertThat(totalDistanceList.size).isEqualTo(6)
            Truth.assertThat(totalDistanceList.first().name).isEqualTo("label")
            Truth.assertThat(totalDistanceList.first().distance).isEqualTo(5000)
            Truth.assertThat(totalDistanceList.first().trainingCount).isEqualTo(35)
            Truth.assertThat(totalDistanceList.first().totalLapCount).isEqualTo(499)
            Truth.assertThat(totalDistanceList.last().name).isEqualTo("track5")
            Truth.assertThat(totalDistanceList.last().distance).isEqualTo(1200)
            Truth.assertThat(totalDistanceList.last().trainingCount).isEqualTo(9)
            Truth.assertThat(totalDistanceList.last().totalLapCount).isEqualTo(99)
        }
    }

    @Test
    fun findBestLapList() {
        runBlocking {
            val trackBestList = repository.findBestLapList()
            Truth.assertThat(trackBestList.size).isEqualTo(2)
            Truth.assertThat(trackBestList.first().trackId).isEqualTo(1)
            Truth.assertThat(trackBestList.first().trackName).isEqualTo("track1")
            Truth.assertThat(trackBestList.first().bestLap).isEqualTo("bestLap1")
            Truth.assertThat(trackBestList.first().lapCount).isEqualTo(11)
            Truth.assertThat(trackBestList.first().trackLength).isEqualTo(101)
            Truth.assertThat(trackBestList.first().startTime).isEqualTo("startTime1")
            Truth.assertThat(trackBestList.first().endTime).isEqualTo("endTime1")
            Truth.assertThat(trackBestList.first().displayTime).isEqualTo("displayTime1")
            Truth.assertThat(trackBestList.last().trackId).isEqualTo(3)
            Truth.assertThat(trackBestList.last().trackName).isEqualTo("track3")
            Truth.assertThat(trackBestList.last().bestLap).isEqualTo("bestLap3")
            Truth.assertThat(trackBestList.last().lapCount).isEqualTo(13)
            Truth.assertThat(trackBestList.last().trackLength).isEqualTo(103)
            Truth.assertThat(trackBestList.last().startTime).isEqualTo("startTime3")
            Truth.assertThat(trackBestList.last().endTime).isEqualTo("endTime3")
            Truth.assertThat(trackBestList.last().displayTime).isEqualTo("displayTime3")
        }
    }

    @Test
    fun getPracticeListByTrack() {
        runBlocking {
            repository.getPracticeListByTrack(1, true)
            coVerify {
                practiceTrackDao.getPracticeListByTrackOrderByBestLap(1)
            }
            repository.getPracticeListByTrack(2, false)
            coVerify {
                practiceTrackDao.getPracticeListByTrackOrderByDate(2)
            }
        }
    }
}