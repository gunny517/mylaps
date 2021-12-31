package jp.ceed.android.mylapslogger.repository

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UserAccountRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Application>()

    private val repository: UserAccountRepository = UserAccountRepository(context)

    @Test
    fun requestLogin() {
        repository.requestLogin(
            "gunny517@gmail.com",
            "gunny517"
        ) {
            assertEquals(true, it.isSuccess)
        }

    }
}