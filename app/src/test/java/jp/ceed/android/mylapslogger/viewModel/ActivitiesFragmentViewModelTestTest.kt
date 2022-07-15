package jp.ceed.android.mylapslogger.viewModel

import android.os.Looper
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object ActivitiesFragmentViewModelTestTest : Spek({

    // テスト実行用のMainLooperの準備
    mockkStatic(Looper::class)
    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }
    every { Looper.getMainLooper() } returns looper

    val isLogin: UserAccountRepository = mockk(relaxed = true) {
        every {
            getAccessToken()
        } returns "access token"
        every {
            getUserId()
        } returns "user id"
    }

    val isNotLogin: UserAccountRepository = mockk(relaxed = true) {
        every {
            getAccessToken()
        } returns null
        every {
            getUserId()
        } returns null
    }

    val apiRepository: ApiRepository = mockk(relaxed = true)

    describe("アカウントチェックの処理") {
        context("ログイン状態による振り分け"){
            it("未ログインの場合ログインイベントが呼ばれる") {
                val viewModel = ActivitiesFragmentViewModel(
                    apiRepository = apiRepository,
                    userAccountRepository = isNotLogin,
                    exceptionUtil = mockk(),
                )
                viewModel.checkAccount()
                assertThat(viewModel.event.value?.peekContent())
                    .isEqualTo(ActivitiesFragmentViewModel.EventState.GO_TO_LOGIN)
            }
            it("ログイン済みの場合Activitiesのリクエストが実行される") {
                val viewModel = ActivitiesFragmentViewModel(
                    apiRepository = apiRepository,
                    userAccountRepository = isLogin,
                    exceptionUtil = mockk(),
                )
                viewModel.checkAccount()
                verify {
                    viewModel.apiRepository.getActivities(any())
                }
            }
        }
    }
})