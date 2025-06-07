package jp.ceed.android.mylapslogger.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.dto.Status
import jp.ceed.android.mylapslogger.extensions.durationToFloat
import jp.ceed.android.mylapslogger.network.response.Sessions
import javax.inject.Inject

class SessionDataCreator @Inject constructor(
    @ApplicationContext val context: Context,
) {

    /**
     * PracticeResultFragment で表示するアイテムを背精して返す
     *
     * @param sessions セッション情報
     * @param sessionNo 表示するセッション No すべての場合は 0 を指定
     * @return PracticeResultFragment で表示するアイテム
     */
    fun createLapList(sessions: Sessions, sessionNo: Int?): List<PracticeResultsItem> {
        val showSpeedBar = AppSettings(context).isShowSpeedBar()
        val lapList = ArrayList<PracticeResultsItem>()
        for (session in sessions.sessions) {
            if(sessionNo != 0 && sessionNo != session.id){
                continue
            }
            lapList.add(PracticeResultsItem.Section(session))
            val sessionBest: Float = parseBestLap(session.bestLap?.duration)
            for (lap in session.laps) {
                val item = PracticeResultsItem.Lap(lap, session)
                if(showSpeedBar){
                    applySpeedLevel(item, sessionBest)
                }
                lapList.add(item)
            }
        }
        calculateSectorData(lapList, sessions.sections.size)
        return lapList
    }

    /**
     * 各ラップごとの各セクタータイムを、ほかの周と比較してベストタイムに関する情報を付与したうえでエンティティに保存する
     *
     * @param lapList 対象のラップリスト
     * @param sectorSize セクター数
     */
    private fun calculateSectorData(lapList: List<PracticeResultsItem>, sectorSize: Int) {
        val fastestSectors: ArrayList<Float> = arrayListOf()
        for (i in 0..<sectorSize) {
            fastestSectors.add(Float.MAX_VALUE)
        }
        // まず全てのラップの中で各セクターごとのベストタイムを探す
        lapList.forEach { lap ->
            if (lap is PracticeResultsItem.Lap) {
                lap.rawSectorValues.forEachIndexed { sectorIndex, sector ->
                    // TODO 瑞浪で Sector2 の区間タイムに不正な値が返却される件の暫定対応
                    if (!lap.isValidSectorTime() && sectorIndex == 1) return@forEach
                    val current = sector.durationToFloat()
                    if (fastestSectors[sectorIndex] > current) {
                        fastestSectors[sectorIndex] = current
                    }
                }
            }
        }
        // 各セクターごとのベストタイムにフラグを立てる
        lapList.forEach { lap ->
            if (lap is PracticeResultsItem.Lap) {
                val sectors: ArrayList<PracticeResultsItem.SectorData> = arrayListOf()
                lap.rawSectorValues.forEachIndexed { sectorIndex, sector ->
                    val current = sector.durationToFloat()
                    val status = if (fastestSectors[sectorIndex] == current) {
                        Status.REPORTBEST
                    } else {
                        Status.NONE
                    }
                    sectors.add(PracticeResultsItem.SectorData(status, current.toString()))
                }
                lap.sectorDataList = sectors
            }
        }
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

    private fun parseBestLap(bestLap: String?): Float {
        if(bestLap == null) {
            return DEFAULT_BEST_LAP_TIME
        }
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