package kaist.iclab.abclogger.auth

abstract class AuthRepository {

    abstract val isSignedIn: Boolean

    abstract val userInfo: UserInfo
    abstract val deviceInfo: DeviceInfo
    abstract val appVersion: String

    // Register Email
    fun signIn() {}
    // Login with Email
    fun Login() {}
    // Logout
    fun Logout() {}
}