package nilezia.app.foodorder.ui.friend

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.UserAuth
import nilezia.app.foodorder.ui.repository.UserRepository

class FriendsPresenter : BaseMvpPresenterImp<FriendsContract.View>(), FriendsContract.Presenter {

    private lateinit var mRepository: UserRepository

    companion object {

        fun create(): FriendsContract.Presenter = FriendsPresenter()

    }

    override fun registerRepository(repository: UserRepository) {

        this.mRepository = repository

    }

    override fun requestUsers() {

        mRepository.getAllUsers(object : CallbackHttp<MutableList<UserInfo>> {
            override fun onSuccess(response: MutableList<UserInfo>) {
                var index = 0;
                response.forEach {
                    if (UserAuth.instance.getUserInfo()?.DisplayName == it.DisplayName) {
                        index = response.indexOf(it)
                    }
                }
                response.removeAt(index)
                getView().onUpdateUsersList(response)

            }

            override fun onFailed(txt: String) {

            }
        })

    }

}