package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem
import jp.ceed.android.mylapslogger.repository.ActivityInfoTrackRepository
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FuelConsumptionListFragmentViewModelTest {

    private val activityInfoTrackRepository: ActivityInfoTrackRepository = mockk {
        coEvery {
            getFuelConsumptionList()
        } returns listOf(
            FuelConsumptionListItem(
                dateTime = "2022-01-01",
                trackName = "trackName",
                fuelConsumption = "1.2",
            )
        )
    }

    private lateinit var viewModel: FuelConsumptionListFragmentViewModel

    @BeforeAll
    fun beforeAll() {
        initMainLooper()
    }

    @BeforeEach
    fun setUp() {
        viewModel = FuelConsumptionListFragmentViewModel(
            activityInfoTrackRepository
        )
    }

    @Test
    fun initialState() {
        assertThat(viewModel.listItem.value).hasSize(1)
        assertThat(viewModel.listItem.value?.get(0)?.fuelConsumption).isEqualTo("1.2")
    }
}