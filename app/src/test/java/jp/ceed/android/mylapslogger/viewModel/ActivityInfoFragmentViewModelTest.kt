package jp.ceed.android.mylapslogger.viewModel

import android.os.Looper
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object ActivityInfoFragmentViewModelTest : Spek({

    // テスト実行用のMainLooperの準備
    mockkStatic(Looper::class)
    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }
    every { Looper.getMainLooper() } returns looper

    val savedStateHandle: SavedStateHandle = mockk(relaxed = true) {
        every {
            get<Int>("activityId")
        } returns 123
        every {
            get<Int>("trackId")
        } returns 111
        every {
            get<String>("dateTime")
        } returns "2022-01-01"
    }

    val activityInfoRepository: ActivityInfoRepository = mockk {
        coEvery {
            findById(123)
        } returns ActivityInfo(
            activityId = 123,
            description = "This is description",
            fuelConsumption = 1.2F,
            trackId = 1,
            dateTime = "2022-01-1",
        )
    }

    val viewModel: ActivityInfoFragmentViewModel = ActivityInfoFragmentViewModel(
        savedStateHandle = savedStateHandle,
        activityInfoRepository = activityInfoRepository,
    )

    describe("loadActivityInfoを実行すると"){
        it("activityInfoの値がフィールドにセットされる") {
            viewModel.loadActivityInfo()
            assertThat(viewModel.description.value).isEqualTo("This is description")
            assertThat(viewModel.fuelConsumption.value).isEqualTo("1.2")
            assertThat(viewModel.isUpdate).isEqualTo(true)
        }
    }
})