package nilezia.app.foodorder.data

data class UserInfo(
        var email: String,
        var DisplayName: String?,
        var avatar: String?) {
    constructor() : this("", "", "")
}
