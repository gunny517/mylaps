package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityInfoFragmentViewModelTest {


    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true) {
        every {
            get<Long>("activityId")
        } returns 123L
        every {
            get<Int>("trackId")
        } returns 111
        every {
            get<String>("dateTime")
        } returns "2022-01-01"
        every {
            get<String>("bestLap")
        } returns "42.85"
        every {
            get<String>("totalTime")
        } returns "1:23:45.678"
        every {
            get<String>("totalDistance")
        } returns "123.45"
        every {
            get<String>("totalLap")
        } returns "12"
    }

    private val hasRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(any())
        } returns ActivityInfo(
            activityId = (savedStateHandle.get<Long>("activityId") ?: 0),
            description = "This is description",
            fuelConsumption = 1.2F,
            trackId = (savedStateHandle.get<Int>("trackId") ?: 0),
            dateTime = (savedStateHandle.get<String>("dateTime") ?: ""),
            eventName = "2024 APG Rd.6"
        )
    }

    private val hasNotRecord: ActivityInfoRepository = mockk {
        coEvery {
            findById(any())
        } returns null
    }

    @BeforeEach
    fun setUp() {
        initMainLooper()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadActivityInfo() {
        // 既に保存されている場合
        var viewModel = ActivityInfoFragmentViewModel(
            savedStateHandle = savedStateHandle,
            activityInfoRepository = hasRecord,
        )

        // パラメータの値がフィールドにセットされる
        assertThat(viewModel.activityId).isEqualTo(123)
        assertThat(viewModel.bestLap.value).isEqualTo("42.85")
        assertThat(viewModel.totalLap.value).isEqualTo("12")
        assertThat(viewModel.totalTime.value).isEqualTo("1:23:45.678")
        assertThat(viewModel.totalDistance.value).isEqualTo("123.45")
        assertThat(viewModel.trackId).isEqualTo(111)
        assertThat(viewModel.dateTime).isEqualTo("2022-01-01")

        // DataBaseから取得した値がフィールドにセットされる
        assertThat(viewModel.description.value).isEqualTo("This is description")
        assertThat(viewModel.fuelConsumption.value).isEqualTo("1.2")
        assertThat(viewModel.eventName.value).isEqualTo("2024 APG Rd.6")

        // 更新モードになっている
        assertThat(viewModel.isUpdate).isEqualTo(true)

        // 保存が実行されると更新処理が実行される
        viewModel.description.value = "This is Updated value."
        viewModel.fuelConsumption.value = "1.5"
        viewModel.saveSessionInfo()
        coVerify {
            viewModel.activityInfoRepository.update(
                ActivityInfo(
                    activityId = 123L,
                    description = "This is Updated value.",
                    fuelConsumption = 1.5F,
                    trackId = 111,
                    dateTime = "2022-01-01",
                    eventName = "2024 APG Rd.6"
                )
            )
        }

        // 保存されていなかった場合
        viewModel = ActivityInfoFragmentViewModel(
            savedStateHandle = savedStateHandle,
            activityInfoRepository = hasNotRecord,
        )

        // パラメータの値がフィールドにセットされる
        assertThat(viewModel.activityId).isEqualTo(123)
        assertThat(viewModel.bestLap.value).isEqualTo("42.85")
        assertThat(viewModel.totalLap.value).isEqualTo("12")
        assertThat(viewModel.totalTime.value).isEqualTo("1:23:45.678")
        assertThat(viewModel.totalDistance.value).isEqualTo("123.45")
        assertThat(viewModel.trackId).isEqualTo(111)
        assertThat(viewModel.dateTime).isEqualTo("2022-01-01")

        // データベースの値がセットされていない
        assertThat(viewModel.description.value).isNull()
        assertThat(viewModel.fuelConsumption.value).isNull()

        // 入力モードになっている
        assertThat(viewModel.isUpdate).isEqualTo(false)

        // 保存が実行されると挿入が実行される
        viewModel.description.value = "This is input value"
        viewModel.fuelConsumption.value = "1.4"
        viewModel.eventName.value = "insert event name"
        viewModel.saveSessionInfo()
        coVerify {
            viewModel.activityInfoRepository.insert(
                ActivityInfo(
                    activityId = 123L,
                    description = "This is input value",
                    fuelConsumption = 1.4F,
                    trackId = 111,
                    dateTime = "2022-01-01",
                    eventName = "insert event name",
                )
            )
        }
    }

    @Test
    fun saveSessionInfo() {
    }

    @Test
    fun calculateFuelConsumption() {
    }
}
