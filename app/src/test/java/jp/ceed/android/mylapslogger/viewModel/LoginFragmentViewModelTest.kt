package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

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

    @BeforeAll
    fun setUp() {
        initMainLooper()
    }

    @Test
    fun callLogin() {
        callLoginSuccess()
        callLoginFailed()
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