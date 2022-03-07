package jp.ceed.android.mylapslogger

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getInstrumentation().targetContext
		assertEquals("jp.ceed.android.mylapslogger", appContext.packageName)
	}

	@Test
	fun activitiesFragmentTest(){
		val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
		val scenario = launchFragmentInContainer<ActivitiesFragment>(factory = MyFragmentFactory())
	}


	class MyFragmentFactory: FragmentFactory() {

		override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
			if(ActivitiesFragment::class.java.name == className){
				return ActivitiesFragment()
			}
			return super.instantiate(classLoader, className)
		}
	}
}