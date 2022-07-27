package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.network.response.LoginResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object UserInfoFragmentViewModelTest : Spek({

    initMainLooper()

    val oAuthResponse: LoginResponse = mockk(relaxed = true) {
        every {
            userId
        } returns "user id for test."
    }

    val userAccountRepository: UserAccountRepository = mockk {
        every {
            getUserInfo()
        } returns oAuthResponse
    }

    val viewModel = UserInfoFragmentViewModel(
        userAccountRepository = userAccountRepository,
    )

    describe("ユーザー情報の読み込み") {
        it("ユーザー情報が正常に読み込まれる") {
            assertThat(viewModel.userInfo.value?.userId).isEqualTo("user id for test.")
        }
    }

})