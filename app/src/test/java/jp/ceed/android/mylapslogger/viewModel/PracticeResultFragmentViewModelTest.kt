package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.ceed.android.mylapslogger.initMainLooper
import jp.ceed.android.mylapslogger.repository.PracticeResultsRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object PracticeResultFragmentViewModelTest : Spek({

    lateinit var viewModel: PracticeResultFragmentViewModel

    initMainLooper()

    val state: SavedStateHandle = mockk {
        every {
            get<Int>("activityId")
        } returns 2
        every {
            get<Int>("trackLength")
        } returns 1003
        every {
            get<Int>("sessionNo")
        } returns 3
    }

    describe("初期化処理の実行") {
        viewModel = PracticeResultFragmentViewModel(
            state = state,
            appSettings = mockk(relaxed = true),
            userAccountRepository = mockk(relaxed = true),
            apiRepository = mockk(),
            weatherRepository = mockk(),
            locationRepository = mockk(),
            sessionInfoRepository = mockk(),
            exceptionUtil = mockk(),
            resourceRepository = mockk(),
            practiceResultsRepository = mockk(),
        )

        it("画面遷移のパラメータがセットされること") {
            assertThat(viewModel.activityId).isEqualTo(2)
            assertThat(viewModel.trackLength).isEqualTo(1003)
            assertThat(viewModel.sessionNo).isEqualTo(3)
        }
    }

    describe("ログイン状態の場合") {

        context("自動読み込みがOFFに設定されている場合") {
            val useAccountRepository: UserAccountRepository = mockk {
                every {
                    getAccessToken()
                } returns "access token"
            }
            viewModel = PracticeResultFragmentViewModel(
                state = state,
                appSettings = mockk {
                    every { isAllowSessionAutoLoading() } returns false
                },
                userAccountRepository = useAccountRepository,
                apiRepository = mockk(),
                weatherRepository = mockk(),
                locationRepository = mockk(),
                sessionInfoRepository = mockk(),
                exceptionUtil = mockk(),
                resourceRepository = mockk(),
                practiceResultsRepository = mockk(),
            )

            it("自動読み込みではない読み込みが実行されること") {
                coVerify {
                    viewModel.apiRepository.getPracticeResult("access token", 2, 1003, 3)
                }
            }

            context("自動読み込みがONに設定されている場合") {

                it("自動読み込みの処理が実行されること") {
                    viewModel = PracticeResultFragmentViewModel(
                        state = state,
                        appSettings = mockk {
                            every { isAllowSessionAutoLoading() } returns true
                        },
                        userAccountRepository = useAccountRepository,
                        apiRepository = mockk(),
                        weatherRepository = mockk(),
                        locationRepository = mockk(),
                        sessionInfoRepository = mockk(),
                        exceptionUtil = mockk(),
                        resourceRepository = mockk(),
                        practiceResultsRepository = mockk(relaxed = true),
                    )
                    coVerify {
                        viewModel.practiceResultsRepository.args = PracticeResultsRepository.Args(
                            2, "access token", 1003, 3
                        )
                        viewModel.practiceResultsRepository.sessionFlow.collect(any())
                    }
                }
            }
        }
    }
})