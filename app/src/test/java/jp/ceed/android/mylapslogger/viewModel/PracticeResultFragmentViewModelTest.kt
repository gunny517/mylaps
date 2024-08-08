package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeResultsRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PracticeResultFragmentViewModelTest {

    private val state: SavedStateHandle = mockk {
        every {
            get<Long>("activityId")
        } returns 2L
        every {
            get<Int>("trackLength")
        } returns 1003
        every {
            get<Int>("sessionNo")
        } returns 3
    }

    private lateinit var viewModel: PracticeResultFragmentViewModel
    @BeforeAll
    fun beforeAll() {
        initMainLooper()
    }

    @Test
    fun initState() {
        viewModel = PracticeResultFragmentViewModel(
            state = state,
            appSettings = mockk(relaxed = true),
            userAccountRepository = mockk(relaxed = true),
            apiRepository = mockk(),
            weatherRepository = mockk(),
            locationRepository = mockk(),
            sessionInfoRepository = mockk(),
            exceptionUtil = mockk(),
            resourceRepository = mockk(),
            practiceResultsRepository = mockk(),
        )
        // 画面遷移のパラメータがセットされること
        assertThat(viewModel.activityId).isEqualTo(2)
        assertThat(viewModel.trackLength).isEqualTo(1003)
        assertThat(viewModel.sessionNo).isEqualTo(3)
    }



    @Test
    fun loadPracticeResult() {
        // 自動読み込みがOFFに設定されている場合
        val useAccountRepository: UserAccountRepository = mockk {
            every {
                getAccessToken()
            } returns "access token"
        }
        viewModel = PracticeResultFragmentViewModel(
            state = state,
            appSettings = mockk {
                every { isAllowSessionAutoLoading() } returns false
            },
            userAccountRepository = useAccountRepository,
            apiRepository = mockk(),
            weatherRepository = mockk(),
            locationRepository = mockk(),
            sessionInfoRepository = mockk(),
            exceptionUtil = mockk(),
            resourceRepository = mockk(),
            practiceResultsRepository = mockk(),
        )
        // 自動読み込みではない読み込みが実行されること
        coVerify {
            viewModel.apiRepository.getPracticeResult("access token", 2, 1003, 3)
        }

        // 自動読み込みがONに設定されている場合
        viewModel = PracticeResultFragmentViewModel(
            state = state,
            appSettings = mockk {
                every { isAllowSessionAutoLoading() } returns true
            },
            userAccountRepository = useAccountRepository,
            apiRepository = mockk(),
            weatherRepository = mockk(),
            locationRepository = mockk(),
            sessionInfoRepository = mockk(),
            exceptionUtil = mockk(),
            resourceRepository = mockk(),
            practiceResultsRepository = mockk(relaxed = true),
        )
        // 自動読み込みの処理が実行されること
        coVerify {
            viewModel.practiceResultsRepository.args = PracticeResultsRepository.Args(
                2, "access token", 1003, 3
            )
            viewModel.practiceResultsRepository.sessionFlow.collect(any())
        }
    }
}