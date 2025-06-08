package jp.ceed.android.mylapslogger.repository

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.entity.Track
import jp.ceed.android.mylapslogger.model.ActivitiesItem
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
class TrackRepositoryTest {

    private val trackDao: TrackDao = mockk<TrackDao>(relaxed = true) {
        every { findAll() } returns listOf(
            Track(1, "track1", 101, 0L),
            Track(2, "track2", 102, 0L),
            Track(3, "track3", 103, 0L),
            Track(5, "track5", 105, 0L)
        )
    }

    private val repository: TrackRepository = TrackRepository(trackDao, Dispatchers.Main)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun saveAll() {
        val created = System.currentTimeMillis()
        runBlocking {
            repository.saveAll(listOf(
                ActivitiesItem(1, 1, "", "", "", "track1", 101),
                ActivitiesItem(2, 2, "", "", "", "track2", 102),
                ActivitiesItem(3, 3, "", "", "", "track3", 103),
                ActivitiesItem(4, 4, "", "", "", "track4", 104),
                ActivitiesItem(6, 6, "", "", "", "track6", 106),
            ), created)
            coVerify {
                trackDao.save(Track(ActivitiesItem(4, 4, "", "", "", "track4", 104), created))
                trackDao.save(Track(ActivitiesItem(6, 6, "", "", "", "track6", 106), created))
            }
        }

    }
}