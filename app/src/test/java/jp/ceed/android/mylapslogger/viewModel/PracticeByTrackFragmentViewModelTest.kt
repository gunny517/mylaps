package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object PracticeByTrackFragmentViewModelTest : Spek({

    initMainLooper()

    val savedStateHandle: SavedStateHandle = mockk(relaxed = true) {
        every {
            get<Int>("trackId")
        } returns 123
    }

    val listItem: PracticeTrack = mockk(relaxed = true) {
        every {
            trackId
        } returns (savedStateHandle.get<Int>("trackId") ?: 0)
    }

    val practiceTrackRepository: PracticeTrackRepository = mockk(relaxed = true) {
        coEvery {
            getPracticeListByTrack(any(), true)
        } returns listOf(listItem)
    }

    val viewModel = PracticeByTrackFragmentViewModel(
        savedStateHandle = savedStateHandle,
        practiceTrackRepository = practiceTrackRepository,
    )

    describe("初期値の確認") {
        it("フィールドに正しい値がセットされている") {
            assertThat(viewModel.practiceTrackList.value).hasSize(1)
            assertThat(viewModel.practiceTrackList.value?.get(0)?.trackId).isEqualTo(123)
        }
    }
})