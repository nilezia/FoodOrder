package nilezia.app.foodorder.model

import nilezia.app.foodorder.data.UserInfo

class UserAuth private constructor() {

    private var mUserInfo: UserInfo? = null

    object Holder {
        val INSTANCE = UserAuth()
    }

    companion object {
        val instance: UserAuth by lazy { Holder.INSTANCE }
    }

    fun setUserInfo(userinfo: UserInfo) {
        this.mUserInfo = userinfo

    }

    fun getUserInfo() = mUserInfo
}

