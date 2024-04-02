package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object ActivityInfoFragmentViewModelTest : Spek({

    initMainLooper()

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
        every {
            get<String>("bestLap")
        } returns "42.85"
        every {
            get<String>("totalTime")
        } returns "1:23:45.678"
        every {
            get<String>("totalDistance")
        } returns "123.45"
        every {
            get<String>("totalLap")
        } returns "12"
    }

    val hasRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(any())
        } returns ActivityInfo(
            activityId = (savedStateHandle.get<Long>("activityId") ?: 0),
            description = "This is description",
            fuelConsumption = 1.2F,
            trackId = (savedStateHandle.get<Int>("trackId") ?: 0),
            dateTime = (savedStateHandle.get<String>("dateTime") ?: ""),
        )
    }

    val hasNotRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(any())
        } returns null
    }

    describe("フィールドの値"){

        context("既に保存されている場合"){
            val viewModel = ActivityInfoFragmentViewModel(
                savedStateHandle = savedStateHandle,
                activityInfoRepository = hasRecord,
            )

            it("パラメータの値がフィールドにセットされる") {
                assertThat(viewModel.activityId).isEqualTo(123)
                assertThat(viewModel.bestLap.value).isEqualTo("42.85")
                assertThat(viewModel.totalLap.value).isEqualTo("12")
                assertThat(viewModel.totalTime.value).isEqualTo("1:23:45.678")
                assertThat(viewModel.totalDistance.value).isEqualTo("123.45")
                assertThat(viewModel.trackId).isEqualTo(111)
                assertThat(viewModel.dateTime).isEqualTo("2022-01-01")
            }

            it("DataBaseから取得した値がフィールドにセットされる") {
                assertThat(viewModel.description.value).isEqualTo("This is description")
                assertThat(viewModel.fuelConsumption.value).isEqualTo("1.2")
            }

            it("更新モードになっている") {
                assertThat(viewModel.isUpdate).isEqualTo(true)
            }

            it("保存が実行されると更新処理が実行される"){
                viewModel.description.value = "This is Updated value."
                viewModel.fuelConsumption.value = "1.5"
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.activityInfoRepository.update(
                        ActivityInfo(
                            activityId = 123L,
                            description = "This is Updated value.",
                            fuelConsumption = 1.5F,
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

            it("パラメータの値がフィールドにセットされる") {
                assertThat(viewModel.activityId).isEqualTo(123)
                assertThat(viewModel.bestLap.value).isEqualTo("42.85")
                assertThat(viewModel.totalLap.value).isEqualTo("12")
                assertThat(viewModel.totalTime.value).isEqualTo("1:23:45.678")
                assertThat(viewModel.totalDistance.value).isEqualTo("123.45")
                assertThat(viewModel.trackId).isEqualTo(111)
                assertThat(viewModel.dateTime).isEqualTo("2022-01-01")
            }

            it("データベースの値がセットされていない"){
                assertThat(viewModel.description.value).isNull()
                assertThat(viewModel.fuelConsumption.value).isNull()
            }

            it("入力モードになっている") {
                assertThat(viewModel.isUpdate).isEqualTo(false)
            }

            it("保存が実行されると挿入が実行される"){
                viewModel.description.value = "This is input value"
                viewModel.fuelConsumption.value = "1.4"
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.activityInfoRepository.insert(
                        ActivityInfo(
                            activityId = 123L,
                            description = "This is input value",
                            fuelConsumption = 1.4F,
                            trackId = 111,
                            dateTime = "2022-01-01"
                        )
                    )
                }
            }
        }
    }
})