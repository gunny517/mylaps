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

    private val sessionInfoRepository = SessionInfoRepository(context, Dispatchers.Main)


    @Before
    fun setUp() {
        GlobalScope.launch {
            sessionInfoRepository.deleteAll()
            sessionInfoRepository.insert(entity1)
            sessionInfoRepository.insert(entity2)
        }
    }

    @After
    fun tearDown(){
    }

    @Test
    fun findBySessionId() {
        GlobalScope.launch {
            val item1 = sessionInfoRepository.findBySessionId(entity1.sessionId)
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
        GlobalScope.launch {
            sessionInfoRepository.insert(entity3)
            val result = sessionInfoRepository.findBySessionId(entity3.sessionId)
            assertThat(result?.sessionId).isEqualTo(entity3.sessionId)
        }
    }

    @Test
    fun update() {
        val expectedDescription = "this is expected value."
        entity2.description = expectedDescription
        GlobalScope.launch {
            sessionInfoRepository.update(entity2)
            val result = sessionInfoRepository.findBySessionId(entity2.sessionId)
            assertThat(result?.description).isEqualTo(expectedDescription)
        }
    }

    @Test
    fun loadWeatherAndSave() {
    }

    @Test
    fun saveSessionInfo() {
    }

    companion object{
        private val entity1 = SessionInfo(
            111L,
            "10",
            "1000",
            "50",
            "entity1",
            DateUtil.createDateTimeString()
        )
        private val entity2 = SessionInfo(
            112L,
            "20",
            "1001",
            "60",
            "entity1",
            DateUtil.createDateTimeString()
        )
        private val entity3 = SessionInfo(
            113L,
            "30",
            "1002",
            "70",
            "entity3",
            DateUtil.createDateTimeString()
        )
    }
}