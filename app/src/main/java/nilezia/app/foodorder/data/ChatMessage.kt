package nilezia.app.foodorder.data

data class ChatMessage(var avatar: String?,
                       var message: String?,
                       var senderId: String?,
                       var type: String?,
                       var userName: String?) {

    companion object {

        const val TYPE_TEXT: String = "text"

        const val TYPE_IMAGE: String = "image"
    }
    constructor() : this("", "", "", "", "")


}
