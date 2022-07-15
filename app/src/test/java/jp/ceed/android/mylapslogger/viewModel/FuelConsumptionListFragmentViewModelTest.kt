package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem
import jp.ceed.android.mylapslogger.repository.ActivityInfoTrackRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object FuelConsumptionListFragmentViewModelTest : Spek({

    initMainLooper()

    val activityInfoTrackRepository: ActivityInfoTrackRepository = mockk {
        coEvery {
            getFuelConsumptionList()
        } returns listOf(FuelConsumptionListItem(
            dateTime = "2022-01-01",
            trackName = "trackName",
            fuelConsumption = "1.2",
        ))
    }

    val viewModel = FuelConsumptionListFragmentViewModel(
        activityInfoTrackRepository
    )

    describe("初期か処理の確認"){
        it("listItemが正しく初期化されている事"){
            assertThat(viewModel.listItem.value).hasSize(1)
            assertThat(viewModel.listItem.value?.get(0)?.fuelConsumption).isEqualTo("1.2")
        }
    }

})