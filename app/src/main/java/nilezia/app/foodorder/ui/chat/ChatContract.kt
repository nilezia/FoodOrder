package nilezia.app.foodorder.ui.chat

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.ui.repository.UserRepository

interface ChatContract {


    interface View : BaseMvpView {

        fun updateMessage(messages: MutableList<Message>)


    }


    interface Presenter : BaseMvpPresenter<View> {

        fun registerUserRepository(repository: UserRepository, userChat: UserInfo)

        fun loadChat()

        fun sendMessage(text: String, type: String)

        fun getUserChat(): UserInfo

        fun getOldMessage(): MutableList<Message>
    }


}