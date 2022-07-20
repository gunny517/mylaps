package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object TotalDistanceFragmentViewModelTest : Spek({

    initMainLooper()

    val listItem: TotalDistance = mockk {
        every {
            id
        } returns 135
    }

    val practiceTrackRepository: PracticeTrackRepository = mockk(relaxed = true) {
        coEvery {
            getTotalDistanceList()
        } returns listOf(listItem)
    }

    val viewModel = TotalDistanceFragmentViewModel(
        practiceTrackRepository = practiceTrackRepository
    )

    describe("初期化処理の確認") {
        it("フィールドが正しく初期化されている") {
            assertThat(viewModel.totalDistanceList.value).hasSize(1)
            assertThat(viewModel.totalDistanceList.value?.get(0)?.id).isEqualTo(135)
        }
    }
})