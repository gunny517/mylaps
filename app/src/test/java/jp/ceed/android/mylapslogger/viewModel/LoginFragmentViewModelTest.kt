package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.IOException

@RunWith(JUnitPlatform::class)
object LoginFragmentViewModelTest : Spek({

    initMainLooper()

    // repositoryのモック（失敗時）
    val repositoryForFail: UserAccountRepository = mockk{
        every {
            requestLogin(any(), any(), captureLambda())
        } answers {
            lambda<(Result<OAuthResponse>) -> Unit>().captured.invoke(
                Result.failure(IOException("unKnown"))
            )
        }
    }

    // repositoryのモック（成功時）
    val repositoryForSuccess: UserAccountRepository = mockk{
        every {
            requestLogin(any(), any(), captureLambda())
        } answers {
            lambda<(Result<OAuthResponse>) -> Unit>().captured.invoke(
                Result.success(OAuthResponse())
            )
        }
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
        verify {
            viewModel.exceptionUtil.save(any(), any())
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