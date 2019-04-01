package nilezia.app.foodorder.ui.chat

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.model.UserAuth
import nilezia.app.foodorder.ui.repository.UserRepository

class ChatPresenter : BaseMvpPresenterImp<ChatContract.View>(), ChatContract.Presenter {

    private lateinit var mUserChat: UserInfo
    private lateinit var mRepository: UserRepository
    private var mOldMessage: MutableList<Message> = mutableListOf()

    companion object {

        fun create(): ChatContract.Presenter = ChatPresenter()

    }


    override fun registerUserRepository(repository: UserRepository, userChat: UserInfo) {

        this.mUserChat = userChat
        this.mRepository = repository
    }

    override fun loadChat() {

        mRepository.getChatByUser(getOldMessage(), getUserChat()._id) { item->
            getView().updateMessage(item)
        }
    }

    override fun sendMessage(text: String, type: String) {

        mRepository.sendMessageToFirebase(text, type, getUserChat()._id, UserAuth.instance.getUserInfo()!!)

    }


    override fun getUserChat(): UserInfo = mUserChat


    override fun getOldMessage(): MutableList<Message> = mOldMessage


}