package jp.ceed.android.mylapslogger.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.PracticeRepository
import jp.ceed.android.mylapslogger.repository.TrackRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
class PracticeDataService(): Service() {

    @Inject lateinit var practiceRepository: PracticeRepository

    @Inject lateinit var apiRepository: ApiRepository

    @Inject lateinit var trackRepository: TrackRepository


    override fun onCreate() {
        super.onCreate()
        if(android.os.Debug.isDebuggerConnected()){
            android.os.Debug.waitForDebugger()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        trackRepository = TrackRepository(applicationContext)
        apiRepository = ApiRepository(applicationContext)
        val activities: List<ActivitiesItem>? = intent?.getParcelableArrayListExtra(PARAM_ACTIVITIES)
        activities?.let {
            updateTrackList(it)
            updatePracticeList(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    private fun updateTrackList(activities: List<ActivitiesItem>){
        GlobalScope.launch {
            trackRepository.saveAll(activities)
        }
    }

    private fun updatePracticeList(activities: List<ActivitiesItem>){
        GlobalScope.launch {
            val practiceIdList = practiceRepository.getPracticeIdList()
            loadAndSavePracticeList(activities, practiceIdList)
        }
    }

    private fun loadAndSavePracticeList(activities: List<ActivitiesItem>, practiceIdList: List<Int>){
        for(entry in activities){
            if(practiceIdList.contains(entry.id)){
                continue
            }
            apiRepository.loadPracticeResultForPracticeTable(entry){
                it.onFailure { t ->
                    ExceptionUtil(applicationContext).save(t, GlobalScope)
                }.onSuccess { practice ->
                    savePractice(practice)
                }
            }
        }
    }

    private fun savePractice(practice: Practice){
        GlobalScope.launch {
            practiceRepository.savePractice(practice)
        }
    }


    companion object{
        const val PARAM_ACTIVITIES = "PARAM_ACTIVITIES"
    }

}