package jp.ceed.android.mylapslogger.viewModel

import android.os.Looper
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.*
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

    val hasRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(123)
        } returns ActivityInfo(
            activityId = 123,
            description = "This is description",
            fuelConsumption = 1.2F,
            trackId = 555,
            dateTime = "2022-05-05",
        )
    }

    val hasNotRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(123)
        } returns null
    }

    describe("フィールドの値"){

        context("既に保存されている場合"){
            val viewModel = ActivityInfoFragmentViewModel(
                savedStateHandle = savedStateHandle,
                activityInfoRepository = hasRecord,
            )
            it("activityInfoの値がフィールドにセットされる") {
                assertThat(viewModel.description.value).isEqualTo("This is description")
                assertThat(viewModel.fuelConsumption.value).isEqualTo("1.2")
                assertThat(viewModel.isUpdate).isEqualTo(true)
            }
            it("saveSessionInfoが呼ばれるとupdateが実行される"){
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.activityInfoRepository.update(
                        ActivityInfo(
                            activityId = 123,
                            description = "This is description",
                            fuelConsumption = 1.2F,
                            trackId = 111,
                            dateTime = "2022-01-01",
                        )
                    )
                }
            }
        }

        context("保存されていなかった場合"){
            val viewModel = ActivityInfoFragmentViewModel(
                savedStateHandle = savedStateHandle,
                activityInfoRepository = hasNotRecord,
            )
            it("値がセットされていない"){
                assertThat(viewModel.description.value).isNull()
                assertThat(viewModel.fuelConsumption.value).isNull()
                assertThat(viewModel.isUpdate).isEqualTo(false)
            }
            it("saveSessionInfoが呼ばれるとinsertが実効される"){
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.activityInfoRepository.insert(
                        ActivityInfo(
                            activityId = 123,
                            description = "",
                            fuelConsumption = null,
                            trackId = 111,
                            dateTime = "2022-01-01"
                        )
                    )
                }
            }
        }
    }

    unmockkAll()
})