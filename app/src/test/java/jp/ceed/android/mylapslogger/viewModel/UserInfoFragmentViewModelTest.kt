package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.network.response.LoginResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserInfoFragmentViewModelTest {

    private val oAuthResponse: LoginResponse = mockk(relaxed = true) {
        every {
            userId
        } returns "user id for test."
    }

    private val userAccountRepository: UserAccountRepository = mockk {
        every {
            getUserInfo()
        } returns oAuthResponse
    }

    private lateinit var viewModel: UserInfoFragmentViewModel
    @BeforeAll
    fun beforeAll() {
        initMainLooper()
        viewModel = UserInfoFragmentViewModel(
            userAccountRepository = userAccountRepository,
        )
    }
    @Test
    fun getUserInfo() {
        assertThat(viewModel.userInfo.value?.userId).isEqualTo("user id for test.")
    }
}