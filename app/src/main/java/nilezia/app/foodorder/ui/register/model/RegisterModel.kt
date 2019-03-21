package nilezia.app.foodorder.ui.register.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class RegisterModel(var userName: String, var password: String, var displayName: String?, var imageLink: String?, var imgPath: String?) {

    constructor() : this("", "", "", "", "")


}
