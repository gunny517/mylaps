package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PracticeByTrackFragmentViewModelTest {

    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true) {
        every {
            get<Int>("trackId")
        } returns 123
    }

    private val listItem: PracticeTrack = mockk(relaxed = true) {
        every {
            trackId
        } returns (savedStateHandle.get<Int>("trackId") ?: 0)
    }

    private val practiceTrackRepository: PracticeTrackRepository = mockk(relaxed = true) {
        coEvery {
            getPracticeListByTrack(any(), true)
        } returns listOf(listItem)
    }

    private lateinit var viewModel: PracticeByTrackFragmentViewModel

    @BeforeAll
    fun beforeAll() {
        initMainLooper()
    }

    @BeforeEach
    fun setUp() {
        viewModel = PracticeByTrackFragmentViewModel(
            savedStateHandle = savedStateHandle,
            practiceTrackRepository = practiceTrackRepository,
        )
    }

    @Test
    fun initState() {
        assertThat(viewModel.practiceTrackList.value).hasSize(1)
        assertThat(viewModel.practiceTrackList.value?.get(0)?.trackId).isEqualTo(123)
    }
}