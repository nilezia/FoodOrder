package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp

interface UserRepositoryContract {

    fun getAllUsers(callbackHttp: CallbackHttp<MutableList<UserInfo>>)

    fun sendMessageToFirebase(text: String, type: String, receiver: String, currentUser: UserInfo)

    fun sendImageToFirebase(path: String, receiver: String, currentUser: UserInfo)
}