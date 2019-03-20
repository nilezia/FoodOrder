package nilezia.app.foodorder.ui.register.model

data class RegisterModel(var userName: String, var password: String, var displayName: String?, var imageLink: String?, var imgPath: String?) {

    constructor() : this("", "", "", "", "")


}
