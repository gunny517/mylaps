package jp.ceed.android.mylapslogger.viewModel

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
object TrackBestFragmentViewModelTest : Spek({

    initMainLooper()

    val listItem: PracticeTrack = mockk(relaxed = true) {
        every {
            trackId
        } returns 123
    }
    val practiceTrackRepository: PracticeTrackRepository = mockk {
        coEvery {
            findBestLapList()
        } returns listOf(listItem)
    }

    val viewModel = TrackBestFragmentViewModel(
        practiceTrackRepository = practiceTrackRepository,
    )

    describe("初期値の確認") {
        it("リストの要素が正常に読み込まれる") {
            assertThat(viewModel.trackBestList.value).hasSize(1)
            assertThat(viewModel.trackBestList.value?.get(0)?.trackId).isEqualTo(123)
        }
    }
})