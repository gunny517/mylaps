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
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SessionListFragmentViewModelTest {

    private val savedStateHandle: SavedStateHandle = mockk {
        every {
            get<Long>("activityId")
        } returns 123
    }

    private val firstItem: List<SessionListItem> = mockk(relaxed = true) {
        every {
            get(0).no
        } returns 123.toString()
    }

    private val apiRepository: ApiRepository = mockk {
        coEvery {
            loadPracticeResultsForSessionList(any(), any())
        } returns firstItem
    }

    private val userAccountRepository: UserAccountRepository = mockk {
        every {
            getAccessToken()
        } returns "user token."
    }

    private lateinit var viewModel: SessionListFragmentViewModel
    @BeforeAll
    fun beforeAll() {
        initMainLooper()
        viewModel = SessionListFragmentViewModel(
            savedStateHandle = savedStateHandle,
            apiRepository = apiRepository,
            userAccountRepository = userAccountRepository,
            exceptionUtil = mockk(),
        )
    }

    @Test
    fun initState() {
        // フィールドが正しく初期化されている
        assertThat(viewModel.activityId).isEqualTo(123)
        assertThat(viewModel.sessionItemList.value?.get(0)?.no).isEqualTo(123.toString())
        assertThat(viewModel.isLoading.value).isEqualTo(false)
    }
}
