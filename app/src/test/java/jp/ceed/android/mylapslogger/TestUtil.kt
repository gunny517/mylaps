package jp.ceed.android.mylapslogger

import android.os.Looper
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.io.InputStreamReader

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

/**
 *
 */
fun <T> loadJsonAsEntity(fileName: String, cls: Class<T>): T{
    val stream = ClassLoader.getSystemResourceAsStream(fileName)
    val reader = InputStreamReader(stream)
    return Gson().fromJson(reader, cls)
}