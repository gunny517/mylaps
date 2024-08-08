package jp.ceed.android.mylapslogger.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import jp.ceed.android.mylapslogger.repository.ResourceRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TotalDistanceFragmentViewModelTest {

    private val headerItem: TotalDistance = mockk(relaxed = true)

    private val listItem: TotalDistance = mockk(relaxed = true) {
        every {
            id
        } returns 135
    }

    private val practiceTrackRepository: PracticeTrackRepository = mockk(relaxed = true) {
        coEvery {
            getTotalDistanceList(any())
        } returns listOf(headerItem, listItem)
    }

    private val resourceRepository: ResourceRepository = mockk(relaxed = true)

    private lateinit var viewModel: TotalDistanceFragmentViewModel
    @BeforeAll
    fun beforeAll() {
        initMainLooper()
        viewModel = TotalDistanceFragmentViewModel(
            practiceTrackRepository = practiceTrackRepository,
            resourceRepository = resourceRepository
        )
    }

    @Test
    fun initState() {
        assertThat(viewModel.totalDistanceList.value).hasSize(2)
        assertThat(viewModel.totalDistanceList.value?.get(1)?.id).isEqualTo(135)
    }
}
