package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.MaintenanceLogList
import jp.ceed.android.mylapslogger.usecase.MaintenanceLogUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MaintenanceLogComposeFragmentViewModelTest {

    private val useCase: MaintenanceLogUseCase = mockk {
        coEvery {
            loadMaintenanceLogList()
        } returns listOf(
            MaintenanceLogList(
                name = "item1",
                logs = listOf(
                    MaintenanceLog(
                        id = 1,
                        issueDate = 1000L,
                        runningTime = 10.0F,
                        itemId = 10,
                        description = "description1",
                        imageUri = "content://test_image_1",
                    ),
                    MaintenanceLog(
                        id = 2,
                        issueDate = 2000L,
                        runningTime = 20.0F,
                        itemId = 20,
                        description = "description2",
                        imageUri = "content://test_image_2",
                    )
                )
            ),
            MaintenanceLogList(
                name = "item2",
                logs = listOf(
                    MaintenanceLog(
                        id = 3,
                        issueDate = 3000L,
                        runningTime = 30.0F,
                        itemId = 30,
                        description = "description3",
                        imageUri = "content://test_image_3",
                    )
                )
            )
        )
    }

    private val viewModel =  MaintenanceLogComposeFragmentViewModel(
        state = mockk<SavedStateHandle>(),
        useCase = useCase
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        initMainLooper()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadMaintenanceLogs() {
        viewModel.loadMaintenanceLogs()
        coVerify {
            useCase.loadMaintenanceLogList()
        }
        val logs = viewModel.maintenanceLogs.value
        assertThat(logs).hasSize(2)
        assertThat(logs.first().name).isEqualTo("item1")
        assertThat(logs.first().logs).hasSize(2)
        assertThat(logs.first().logs.first().id).isEqualTo(1)
        assertThat(logs.first().logs.first().issueDate).isEqualTo(1000L)
        assertThat(logs.first().logs.first().runningTime).isEqualTo(10.0F)
        assertThat(logs.first().logs.first().itemId).isEqualTo(10)
        assertThat(logs.first().logs.first().description).isEqualTo("description1")
        assertThat(logs.first().logs.first().imageUri).isEqualTo("content://test_image_1")
        assertThat(logs.first().logs.last().id).isEqualTo(2)
        assertThat(logs.first().logs.last().issueDate).isEqualTo(2000L)
        assertThat(logs.first().logs.last().runningTime).isEqualTo(20.0F)
        assertThat(logs.first().logs.last().itemId).isEqualTo(20)
        assertThat(logs.first().logs.last().description).isEqualTo("description2")
        assertThat(logs.first().logs.last().imageUri).isEqualTo("content://test_image_2")
        assertThat(logs.last().logs).hasSize(1)
        assertThat(logs.last().name).isEqualTo("item2")
        assertThat(logs.last().logs.first().id).isEqualTo(3)
        assertThat(logs.last().logs.first().issueDate).isEqualTo(3000L)
        assertThat(logs.last().logs.first().runningTime).isEqualTo(30.0F)
        assertThat(logs.last().logs.first().itemId).isEqualTo(30)
        assertThat(logs.last().logs.first().description).isEqualTo("description3")
        assertThat(logs.last().logs.first().imageUri).isEqualTo("content://test_image_3")
    }

    @Test
    fun onClickFab() {
        viewModel.onClickFab()
        assertThat(viewModel.event.value?.getContentIfNotHandled()?.name).isEqualTo(EventState.ACTION_ADD.name)
    }

    @Test
    fun onClickLogItem() {
        viewModel.onClickLogItem(4)
        assertThat(viewModel.selectedLogId).isEqualTo(4)
        assertThat(viewModel.event.value?.getContentIfNotHandled()?.name).isEqualTo(EventState.ITEM_SELECTED.name)
    }
}