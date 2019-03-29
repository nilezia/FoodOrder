package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp

interface UserRepositoryContract {

    fun getAllUsers(callbackHttp: CallbackHttp<MutableList<UserInfo>>)

    fun getUserInfo(listener: () -> Unit)
}