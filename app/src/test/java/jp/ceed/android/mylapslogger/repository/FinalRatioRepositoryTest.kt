package jp.ceed.android.mylapslogger.repository

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.MockSharedPreference
import jp.ceed.android.mylapslogger.dto.FinalRatioDto
import jp.ceed.android.mylapslogger.model.FinalRatioResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FinalRatioRepositoryTest {

    private val context = mockk<Context> {
        every {
            getSharedPreferences(any(), any())
        } returns MockSharedPreference()
    }

    private val repository = FinalRatioRepository(context)

    @Test
    fun getFinalRatioData() {
        val result = repository.getFinalRatioData("10", "11", "70", "71")
        val expected = FinalRatioResult(
            arrayListOf("", "10", "11"),
            arrayListOf("70", "7.00", "6.36", "71", "7.10", "6.45")
        )
        assertEquals(expected, result)
    }

    @Test
    fun getSavedValue() {
        repository.getFinalRatioData("10", "11", "70", "71")
        val result = repository.getSavedValue()
        val expected = FinalRatioDto(10, 11, 70, 71)
        assertEquals(expected, result)
    }
}
