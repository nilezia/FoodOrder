package nilezia.app.foodorder.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
class FoodItem {

    @SerializedName("_id")
    var _id: Int = 0
    var amount: Int = 1
    @SerializedName("description")
    var description: String? = null
    @SerializedName("image")
    var image: String? = null

    var isAdded: Boolean? = false
    @SerializedName("name")
    var name: String? = null
    @SerializedName("price")
    var price: Double = .0
    @SerializedName("quantity")
    var quantity: Int = 0

    constructor() {

    }

/*    {
        "_id" : 0,
        "amount" : 1,
        "description" : "บุบกระเทียม บุบพริกชี้ฟ้า(สับเฉียงๆ) และสับๆ ใส่น้ำมันพืช เอากระเทียมกับพริกที่สับไว้ ลงไปผัด จนได้กลิ่น",
        "image" : "http://www.rayonghip.com/wp-content/uploads/2017/03/Joke_Banlang-02.jpg",
        "name" : "กระเพรา",
        "price" : 50,
        "quantity" : 0
    }*/

    constructor(_id: Int,
                amount: Int,
                description: String?,
                image: String?,
                isAdded: Boolean?,
                name: String?,
                price: Double,
                quantity: Int) {

        this._id = _id
        this.amount = amount
        this.description = description
        this.image = image
        this.isAdded = isAdded
        this.name = name
        this.price = price
        this.quantity = quantity
    }
}

