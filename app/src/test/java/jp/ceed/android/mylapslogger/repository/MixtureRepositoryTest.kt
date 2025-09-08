package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class MixtureRepositoryTest {

    private val repository = MixtureRepository()

    @Test
    fun calculate() {
        assertThat(repository.calculate(3120F, 25F, 3000F, 30F)).isEqualTo(80F)
        assertThat(repository.calculate(1040F, 25F, 2500F, 35F)).isEqualTo(60.00006F)
        assertThat(repository.calculate(3100F, 30F, 1000F, 40F)).isEqualTo(0F)
        assertThat(repository.calculate(2600F, 25F, 2000F, 30F)).isEqualTo(50.0F)
    }


}