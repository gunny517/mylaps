package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginFragmentViewModelTest {

    // repositoryのモック（失敗時）
    private val repositoryForFail: UserAccountRepository = mockk{
        coEvery {
            requestLogin(any(), any())
        } returns false
    }

    // repositoryのモック（成功時）
    private val repositoryForSuccess: UserAccountRepository = mockk{
        coEvery {
            requestLogin(any(), any())
        } returns true
    }

    @BeforeEach
    fun setUp() {
        initMainLooper()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun callLoginSuccess() {
        val viewModel = LoginFragmentViewModel(
            userAccountRepository = repositoryForSuccess,
            exceptionUtil = mockk(relaxed = true),
        )
        viewModel.callLogin()
        assertThat(viewModel.loginResult.value).isEqualTo(LoginResult.Success)
    }

    private fun callLoginFailed() {
        val viewModel = LoginFragmentViewModel(
            userAccountRepository = repositoryForFail,
            exceptionUtil = mockk(relaxed = true),
        )
        viewModel.callLogin()
        assertThat(viewModel.loginResult.value).isEqualTo(LoginResult.Failed)
    }
}