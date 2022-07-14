package jp.ceed.android.mylapslogger.viewModel

import android.os.Looper
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import jp.ceed.android.mylapslogger.repository.AppInfoRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object AppInfoFragmentViewModelTest : Spek({

    // テスト実行用のMainLooperの準備
    mockkStatic(Looper::class)
    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }
    every { Looper.getMainLooper() } returns looper

    val appSettings: AppSettings = mockk {
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

    val viewModel: AppInfoFragmentViewModel = AppInfoFragmentViewModel(
        appSettings = appSettings,
        appInfoRepository = appInfoRepository,
    )

    describe("初期状態の確認"){
        assertThat(viewModel.appVersionName.value).isEqualTo("1.2.3")
        assertThat(viewModel.showPracticeResultAsSeparate.value).isEqualTo(true)
        assertThat(viewModel.showSpeedBar.value).isEqualTo(true)
    }

    describe("saveShowPracticeResultsAsSeparateが呼ばれたとき"){
        viewModel.showPracticeResultAsSeparate.value = false
        viewModel.saveShowPracticeResultsAsSeparate()
        verify {
            appSettings.saveShowPracticeResultsAsSeparate(false)
        }
    }
})