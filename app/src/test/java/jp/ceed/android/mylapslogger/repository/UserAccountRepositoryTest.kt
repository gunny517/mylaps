package jp.ceed.android.mylapslogger.repository

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserAccountRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Application>()

    private val repository: UserAccountRepository = UserAccountRepository(context)

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