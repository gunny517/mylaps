package jp.ceed.android.mylapslogger.repository

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserAccountRepositoryTest {

    private lateinit var repository: UserAccountRepository

    @Test
    fun requestLogin() {
        repository.requestLogin(
            USER_NAME,
            PASSWORD
        ) {
            assertThat(it.isSuccess).isTrue()
        }
    }

    companion object {
        const val USER_NAME = "gunny517@gmail.com"
        const val PASSWORD = "gunny517"
    }
}