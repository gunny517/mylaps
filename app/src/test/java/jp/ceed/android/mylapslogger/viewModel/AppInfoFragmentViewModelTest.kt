package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.AppInfoRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object AppInfoFragmentViewModelTest : Spek({

    initMainLooper()

    val appSettings: AppSettings = mockk(relaxed = true) {
        every {
            isShowPracticeResultsAsSeparate()
        } returns true
        every {
            isShowSpeedBar()
        } returns true
    }

    val appInfoRepository: AppInfoRepository = mockk {
        every {
            getVersionName()
        } returns "1.2.3"
    }

    val viewModel = AppInfoFragmentViewModel(
        appSettings = appSettings,
        appInfoRepository = appInfoRepository,
    )

    describe("初期状態の確認"){
        it("正しい値がセットされている"){
            assertThat(viewModel.appVersionName.value).isEqualTo("1.2.3")
            assertThat(viewModel.showPracticeResultAsSeparate.value).isEqualTo(true)
            assertThat(viewModel.showSpeedBar.value).isEqualTo(true)
        }
    }

    describe("saveShowPracticeResultsAsSeparateが呼ばれたとき"){
        it("保存の処理が呼ばれている"){
            viewModel.showPracticeResultAsSeparate.value = false
            viewModel.saveShowPracticeResultsAsSeparate()
            verify {
                viewModel.appSettings.saveShowPracticeResultsAsSeparate(false)
            }
        }
    }

    describe("saveShowSpeedBarが呼ばれえた時"){
        it("保存の処理が呼ばれている"){
            viewModel.showSpeedBar.value = false
            viewModel.saveShowSpeedBar()
            verify {
                viewModel.appSettings.saveSpeedBar(false)
            }
        }
    }
})