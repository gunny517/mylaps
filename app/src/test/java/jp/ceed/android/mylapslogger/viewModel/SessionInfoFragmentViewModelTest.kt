package jp.ceed.android.mylapslogger.viewModel

import android.os.Looper
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import jp.ceed.android.mylapslogger.args.SessionInfoFragmentParams
import jp.ceed.android.mylapslogger.entity.SessionInfo
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object SessionInfoFragmentViewModelTest : Spek({

    // テスト実行用のMainLooperの準備
    mockkStatic(Looper::class)
    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }
    every { Looper.getMainLooper() } returns looper

    // フラグメントに渡されるパラメータの準備
    val params = SessionInfoFragmentParams(12345L, "average", "median")
    val savedStateHandle = mockk<SavedStateHandle> {
        every {
            get<SessionInfoFragmentParams>("params")
        } returns params
    }

    // ViewModel生成
    val viewModel = SessionInfoFragmentViewModel(
        savedStateHandle = savedStateHandle,
        sessionInfoRepository = mockk(relaxed = true),
        locationRepository = mockk(relaxed = true),
        weatherRepository = mockk(relaxed = true),
        exceptionUtil = mockk(relaxed = true),
    )

    viewModel.sessionInfoRepository = mockk()
    coEvery {
        viewModel.sessionInfoRepository.findBySessionId(12345L)
    } returns SessionInfo(12345L)

    describe("#loadSessionInfo"){
        it("セッションがロードされること"){
            viewModel.loadSessionInfo(12345L)
            assertThat(viewModel.sessionInfo.value?.sessionId).isEqualTo(12345L)
        }
    }

    describe("#clearEditText"){
        it("クリアされている事"){
            viewModel.sessionInfo.value = SessionInfo(
                sessionId = 12345L,
                description = "this is description")
            viewModel.clearEditText()
            assertThat(viewModel.sessionInfo.value?.description).isNull()
        }
    }
})