package nilezia.app.foodorder.data

class Message {

    var avatar: String? = null
    var message: String? = null
    var senderId: String? = null
    var type: String? = null
    var userName: String? = null
    var receiverId: String? = null

    companion object {

        const val TYPE_TEXT: String = "text"

        const val TYPE_IMAGE: String = "image"
    }

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue()
    }

    constructor(avatar: String?,
                message: String?,
                senderId: String?,
                receiverId: String?,
                type: String?,
                userName: String?) {

        this.avatar = avatar
        this.message = message
        this.userName = userName
        this.senderId = senderId
        this.type = type
        this.receiverId = receiverId


    }


}