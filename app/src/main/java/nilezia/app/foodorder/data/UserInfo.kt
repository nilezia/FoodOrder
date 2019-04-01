package nilezia.app.foodorder.data

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class UserInfo(
        var _id: String,
        var email: String,
        var DisplayName: String?,
        var avatar: String?) {
    constructor() : this("", "", "", "")
}
