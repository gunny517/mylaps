package jp.ceed.android.mylapslogger.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.network.response.Sessions
import javax.inject.Inject

class SessionDataCreator @Inject constructor(
    @ApplicationContext val context: Context,
) {

    fun createLapList(sessions: Sessions, sessionNo: Int?): List<PracticeResultsItem> {
        val showSpeedBar = AppSettings(context).isShowSpeedBar()
        val lapList = ArrayList<PracticeResultsItem>()
        for (session in sessions.sessions) {
            if(sessionNo != 0 && sessionNo != session.id){
                continue
            }
            lapList.add(PracticeResultsItem.Section(session))
            val sessionBest: Float = parseBestLap(session.bestLap.duration)
            for (lap in session.laps) {
                val item = PracticeResultsItem.Lap(lap, session)
                if(showSpeedBar){
                    applySpeedLevel(item, sessionBest)
                }
                lapList.add(item)
            }
        }
        return lapList
    }

    fun createSessionData(sessions: Sessions): List<PracticeResultsItem> {
        val list: ArrayList<PracticeResultsItem> = ArrayList()
        for (session in sessions.sessions) {
            list.add(PracticeResultsItem.Section(session))
            list.add(PracticeResultsItem.Summary(session))
        }
        return list
    }

    private fun applySpeedLevel(item: PracticeResultsItem.Lap, sessionBest: Float) {
        try {
            val duration = item.duration.toFloat()
            item.speedLevel = (duration - (sessionBest - BEST_LAP_OFFSET)) * 0.1f
        } catch (e: NumberFormatException) {
            item.speedLevel = 0f
        }
    }

    private fun parseBestLap(bestLap: String): Float {
        return try {
            bestLap.toFloat()
        } catch (e: NumberFormatException) {
            DEFAULT_BEST_LAP_TIME
        }
    }

    companion object {
        const val DEFAULT_BEST_LAP_TIME = 30.0f
        const val BEST_LAP_OFFSET = 2f
    }

}