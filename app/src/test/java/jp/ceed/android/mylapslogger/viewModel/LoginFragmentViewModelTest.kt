package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object LoginFragmentViewModelTest : Spek({

    initMainLooper()

    // repositoryのモック（失敗時）
    val repositoryForFail: UserAccountRepository = mockk{
        coEvery {
            requestLogin(any(), any())
        } returns false
    }

    // repositoryのモック（成功時）
    val repositoryForSuccess: UserAccountRepository = mockk{
        coEvery {
            requestLogin(any(), any())
        } returns true
    }

    describe("ログイン失敗時") {
        val viewModel = LoginFragmentViewModel(
            userAccountRepository = repositoryForFail,
            exceptionUtil = mockk(relaxed = true),
        )
        viewModel.callLogin()
        it("失敗時の処理が実行される") {
            assertThat(viewModel.loginResult.value).isEqualTo(LoginResult.Failed)
        }
    }

    describe("ログイン成功時") {
        val viewModel = LoginFragmentViewModel(
            userAccountRepository = repositoryForSuccess,
            exceptionUtil = mockk(relaxed = true),
        )
        viewModel.callLogin()
        it("成功時の処理が実行される") {
            assertThat(viewModel.loginResult.value).isEqualTo(LoginResult.Success)
        }
    }
})