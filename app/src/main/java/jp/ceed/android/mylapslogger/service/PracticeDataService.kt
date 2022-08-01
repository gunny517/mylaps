package jp.ceed.android.mylapslogger.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.PracticeRepository
import jp.ceed.android.mylapslogger.repository.TrackRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.DateUtil
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class PracticeDataService @Inject constructor (): Service() {

    @Inject lateinit var practiceRepository: PracticeRepository

    @Inject lateinit var apiRepository: ApiRepository

    @Inject lateinit var trackRepository: TrackRepository

    @Inject lateinit var exceptionUtil: ExceptionUtil

    @Inject lateinit var userAccountRepository: UserAccountRepository

    override fun onCreate() {
        super.onCreate()
        if(android.os.Debug.isDebuggerConnected()){
            android.os.Debug.waitForDebugger()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        (applicationContext)
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
        userAccountRepository.getAccessToken()?.let { token ->
            GlobalScope.launch {
                val practiceIdList = practiceRepository.getPracticeIdList()
                for(entry in activities){
                    if(practiceIdList.contains(entry.id)){
                        continue
                    }
                    if(DateUtil.isToday(entry.startTime)){
                        continue
                    }
                    try {
                        savePractice(practice = apiRepository.loadPracticeResultForPracticeTable(
                                token = token,
                                activitiesItem = entry,
                            )
                        )
                    } catch (e: Exception) {
                        exceptionUtil.save(e, GlobalScope)
                    }
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