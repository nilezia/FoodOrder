package nilezia.app.foodorder.http

interface CallbackHttp<T> {

    fun onSuccess(response: T)

    fun onFailed(txt: String)


}