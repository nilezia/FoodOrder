package nilezia.app.foodorder.ui.friend

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.ui.repository.UserRepository

interface FriendsContract {


    interface View : BaseMvpView {
        fun onUpdateUsersList(items: MutableList<UserInfo>)
    }

    interface Presenter : BaseMvpPresenter<View> {


        fun registerRepository(repository: UserRepository)

        fun requestUsers()

    }

}