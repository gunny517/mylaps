package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object SessionListFragmentViewModelTest : Spek({

    initMainLooper()

    val savedStateHandle: SavedStateHandle = mockk {
        every {
            get<Int>("activityId")
        } returns 123
    }

    val firstItem: List<SessionListItem> = mockk(relaxed = true) {
        every {
            get(0).no
        } returns 123.toString()
    }

    val apiRepository: ApiRepository = mockk {
        coEvery {
            loadPracticeResultsForSessionList(any(), any())
        } returns firstItem
    }

    val userAccountRepository: UserAccountRepository = mockk {
        every {
            getAccessToken()
        } returns "user token."
    }

    val viewModel = SessionListFragmentViewModel(
        savedStateHandle = savedStateHandle,
        apiRepository = apiRepository,
        userAccountRepository = userAccountRepository,
        exceptionUtil = mockk(),
    )

    describe("初期状態の確認") {
        it("フィールドが正しく初期化されている") {
            assertThat(viewModel.activityId).isEqualTo(123)
            assertThat(viewModel.sessionItemList.value?.get(0)?.no).isEqualTo(123.toString())
            assertThat(viewModel.isLoading.value).isEqualTo(false)
        }
    }

})