package jp.ceed.android.mylapslogger.model

sealed class LoginResult {
	object NotStarted : LoginResult()
	object Success : LoginResult()
	object Failed : LoginResult()
}