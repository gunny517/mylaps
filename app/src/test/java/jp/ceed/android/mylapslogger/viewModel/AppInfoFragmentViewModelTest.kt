package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.AppInfoRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppInfoFragmentViewModelTest {

    private val appSettings: AppSettings = mockk(relaxed = true) {
        every {
            isShowPracticeResultsAsSeparate()
        } returns true
        every {
            isShowSpeedBar()
        } returns true
    }

    private val appInfoRepository: AppInfoRepository = mockk {
        every {
            getVersionName()
        } returns "1.2.3"
    }

    private lateinit var viewModel: AppInfoFragmentViewModel

    @BeforeAll
    fun beforeAll() {
        initMainLooper()
    }

    @BeforeEach
    fun setUp() {
        viewModel = AppInfoFragmentViewModel(
            appSettings = appSettings,
            appInfoRepository = appInfoRepository,
        )
    }

    @Test
    fun initialState() {
        assertThat(viewModel.appVersionName.value).isEqualTo("1.2.3")
        assertThat(viewModel.showPracticeResultAsSeparate.value).isEqualTo(true)
        assertThat(viewModel.showSpeedBar.value).isEqualTo(true)
    }

    @Test
    fun saveShowPracticeResultsAsSeparate() {
        viewModel.saveShowPracticeResultsAsSeparate(true)
        verify {
            viewModel.appSettings.saveShowPracticeResultsAsSeparate(true)
        }
    }

    @Test
    fun saveShowSpeedBar() {
        viewModel.saveShowSpeedBar(false)
        verify {
            viewModel.appSettings.saveSpeedBar(false)
        }
    }

    @Test
    fun saveAllowSessionAutoLoading() {
        viewModel.saveAllowSessionAutoLoading(false)
        verify {
            viewModel.appSettings.saveAllowSessionAutoLoading(false)
        }
    }
}