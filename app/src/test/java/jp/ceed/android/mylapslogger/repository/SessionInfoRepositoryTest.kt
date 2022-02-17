package jp.ceed.android.mylapslogger.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.util.DateUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@DelicateCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SessionInfoRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext() as Context

    private val sessionInfoRepository = SessionInfoRepository(context)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        GlobalScope.launch {
            sessionInfoRepository.deleteAll()
            sessionInfoRepository.insert(entity1)
            sessionInfoRepository.insert(entity2)
        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun findBySessionId() {
        GlobalScope.launch {
            val item1 = sessionInfoRepository.findBySessionId(SESSION_ID_1)
            assertThat(item1).isEqualTo(entity1)
        }
    }

    @Test
    fun findAll() {
        GlobalScope.launch {
            val items = sessionInfoRepository.findAll()
            assertThat(items).hasSize(2)
        }
    }

    @Test
    fun insert() {
    }

    @Test
    fun update() {
    }

    @Test
    fun loadWeatherAndSave() {
    }

    @Test
    fun saveSessionInfo() {
    }

    companion object{
        private const val SESSION_ID_1 = 111L
        private const val SESSION_ID_2 = 112L
        private val entity1 = SessionInfo(
            SESSION_ID_1,
            "10",
            "1000",
            "50",
            "entity1",
            DateUtil.createDateTimeString()
        )
        private val entity2 = SessionInfo(
            SESSION_ID_2,
            "20",
            "1001",
            "60",
            "entity1",
            DateUtil.createDateTimeString()
        )
    }
}