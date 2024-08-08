package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrackBestFragmentViewModelTest {

    private val listItem: PracticeTrack = mockk(relaxed = true) {
        every {
            trackId
        } returns 123
    }
    private val practiceTrackRepository: PracticeTrackRepository = mockk {
        coEvery {
            findBestLapList()
        } returns listOf(listItem)
    }

    private lateinit var viewModel: TrackBestFragmentViewModel
    @BeforeAll
    fun beforeAll() {
        initMainLooper()
        viewModel = TrackBestFragmentViewModel(
            practiceTrackRepository = practiceTrackRepository,
        )
    }

    @Test
    fun initState() {
        assertThat(viewModel.trackBestList.value).hasSize(1)
        assertThat(viewModel.trackBestList.value?.get(0)?.trackId).isEqualTo(123)
    }
}
