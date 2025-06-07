package jp.ceed.android.mylapslogger.dto

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class PracticeResultsItemTest {

    @Test
    fun siValidSectorTime() {
        assertThat(
            PracticeResultsItem.Lap().apply {
                rawSectorValues = listOf("20.00", "20.00", "10.50")
            }.isValidSectorTime()
        ).isEqualTo(false)
        assertThat(
            PracticeResultsItem.Lap().apply {
                duration = "50.50"
                rawSectorValues = listOf("20.00", "20.00", "")
            }.isValidSectorTime()
        ).isEqualTo(false)
        assertThat(
            PracticeResultsItem.Lap().apply {
                duration = "50.50"
                rawSectorValues = listOf("20.00", "20.00", "10.50")
            }.isValidSectorTime()
        ).isEqualTo(true)
        assertThat(
            PracticeResultsItem.Lap().apply {
                duration = "50.50"
                rawSectorValues = listOf("20.00", "10.00", "10.50")
            }.isValidSectorTime()
        ).isEqualTo(false)
        assertThat(
            PracticeResultsItem.Lap().apply {
                duration = "57.190"
                rawSectorValues = listOf("29.120", "28.070")
            }.isValidSectorTime()
        ).isEqualTo(true)
    }
}