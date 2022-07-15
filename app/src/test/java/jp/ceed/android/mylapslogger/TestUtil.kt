package jp.ceed.android.mylapslogger

import android.os.Looper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic

/**
 * テスト実行用のMainLooperの準備
 */
fun initMainLooper(){
    mockkStatic(Looper::class)
    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }
    every { Looper.getMainLooper() } returns looper
}