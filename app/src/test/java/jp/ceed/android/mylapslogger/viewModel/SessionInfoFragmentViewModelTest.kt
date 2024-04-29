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
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SessionInfoFragmentViewModelTest {

    // フラグメントに渡されるパラメータの準備
    private val params = SessionInfoFragmentParams(12345L, "average", "median")
    private val savedStateHandle = mockk<SavedStateHandle> {
        every {
            get<SessionInfoFragmentParams>("params")
        } returns params
    }

    private lateinit var viewModel: SessionInfoFragmentViewModel

    @BeforeAll
    fun beforeAll() {
        initMainLooper()
        viewModel = SessionInfoFragmentViewModel(
            savedStateHandle = savedStateHandle,
            sessionInfoRepository = mockk(relaxed = true),
            locationRepository = mockk(relaxed = true),
            weatherRepository = mockk(relaxed = true),
            exceptionUtil = mockk(relaxed = true),
        )
    }

    @Test
    fun clearEditTest() {
        // 入力内容がクリアされている
        viewModel.sessionInfo.value = SessionInfo(
            sessionId = 12345L,
            description = "this is description")
        viewModel.clearEditText()
        assertThat(viewModel.sessionInfo.value?.description).isNull()
        assertThat(viewModel.sessionInfo.value?.humidity).isNull()
        assertThat(viewModel.sessionInfo.value?.temperature).isNull()
        assertThat(viewModel.sessionInfo.value?.pressure).isNull()
    }

    @Test
    fun saveSessionInfo() {
        // 上書きモードの時はupdateが実行されること
        viewModel.loadSessionInfo(12345L)
        viewModel.saveSessionInfo()
        coVerify {
            viewModel.sessionInfoRepository.update(viewModel.sessionInfo.value ?: throw AssertionError())
        }
        // 入力モードの時はinsertが実行されること
        viewModel.loadSessionInfo(12345L)
        viewModel.isInsert = true
        viewModel.saveSessionInfo()
        coVerify {
            viewModel.sessionInfoRepository.insert(viewModel.sessionInfo.value ?: throw AssertionError())
        }
    }

    @Test
    fun loadSessionInfo() {
        viewModel.sessionInfoRepository = mockk()
        coEvery {
            viewModel.sessionInfoRepository.findBySessionId(12345L)
        } returns SessionInfo(12345L)
        // セッション情報がロードされている
        viewModel.loadSessionInfo(12345L)
        assertThat(viewModel.sessionInfo.value?.sessionId).isEqualTo(12345L)
    }
}
