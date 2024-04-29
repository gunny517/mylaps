package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivitiesFragmentViewModelTest {

    private val isLogin: UserAccountRepository = mockk(relaxed = true) {
        every {
            getAccessToken()
        } returns "access token"
        every {
            getUserId()
        } returns "user id"
    }

    private val isNotLogin: UserAccountRepository = mockk(relaxed = true) {
        every {
            getAccessToken()
        } returns null
        every {
            getUserId()
        } returns null
    }

    private val apiRepository: ApiRepository = mockk(relaxed = true)

    @BeforeAll
    fun beforeAll() {
        initMainLooper()
    }

    @Test
    fun checkAccount() {

        // 未ログインの場合ログインイベントが呼ばれる
        var viewModel = ActivitiesFragmentViewModel(
            apiRepository = apiRepository,
            userAccountRepository = isNotLogin,
            exceptionUtil = mockk(),
        )
        viewModel.checkAccount()
        assertThat(viewModel.event.value?.peekContent())
            .isEqualTo(ActivitiesFragmentViewModel.EventState.GO_TO_LOGIN)

        // ログイン済みの場合Activitiesのリクエストが実行される
        viewModel = ActivitiesFragmentViewModel(
            apiRepository = apiRepository,
            userAccountRepository = isLogin,
            exceptionUtil = mockk(),
        )
        viewModel.checkAccount()
        coVerify {
            viewModel.apiRepository.getActivities(any())
        }
    }
}