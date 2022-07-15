package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.args.SessionInfoFragmentParams
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.initMainLooper
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object SessionInfoFragmentViewModelTest : Spek({

    initMainLooper()

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

    describe("loadSessionInfoを実行すると"){
        it("セッション情報がロードされている"){
            viewModel.loadSessionInfo(12345L)
            assertThat(viewModel.sessionInfo.value?.sessionId).isEqualTo(12345L)
        }
    }

    describe("clearEditTextを実行すると"){
        it("入力内容がクリアされている"){
            viewModel.sessionInfo.value = SessionInfo(
                sessionId = 12345L,
                description = "this is description")
            viewModel.clearEditText()
            assertThat(viewModel.sessionInfo.value?.description).isNull()
            assertThat(viewModel.sessionInfo.value?.humidity).isNull()
            assertThat(viewModel.sessionInfo.value?.temperature).isNull()
            assertThat(viewModel.sessionInfo.value?.pressure).isNull()
        }
    }

    describe("saveSessionInfoを実行すると"){
        context("上書きモードの時"){
            it("updateが実行されること"){
                viewModel.loadSessionInfo(12345L)
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.sessionInfoRepository.update(viewModel.sessionInfo.value ?: throw AssertionError())
                }
            }
        }
        context("入力モードの時"){
            it("insertが実行されること"){
                viewModel.loadSessionInfo(12345L)
                viewModel.isInsert = true
                viewModel.saveSessionInfo()
                coVerify {
                    viewModel.sessionInfoRepository.insert(viewModel.sessionInfo.value ?: throw AssertionError())
                }
            }
        }
    }
})