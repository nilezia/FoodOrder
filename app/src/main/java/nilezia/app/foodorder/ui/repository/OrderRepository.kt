package nilezia.app.foodorder.ui.repository

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.OrderItem


class OrderRepository(context: Context) : OrderRepositoryContract {


    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food-order")


    override fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {


    }

    override fun requestOrders(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

        requestOrderFromFirebase(callbackHttp)

    }

    override fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orders = mutableListOf<OrderItem>()

                dataSnapshot.children.forEach {

                    val value = it.getValue(OrderItem::class.java)

                    Log.d("FirebaseRead", "Value is: ${value?.name}")
                   orders.add(value!!)

                }

                callbackHttp.onSuccess(orders)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseRead", "Failed to read value.", error.toException())
            }
        })


    }

    override fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

//        val orders = mutableListOf<OrderItem>()
//        for (i in 0..6) {
//            val order: OrderItem? = when (i) {
//                0 -> OrderItem(i, "กระเพรา", 50.00, 0, 1, "http://www.rayonghip.com/wp-content/uploads/2017/03/Joke_Banlang-02.jpg", "บุบกระเทียม บุบพริกชี้ฟ้า(สับเฉียงๆ) และสับๆ ใส่น้ำมันพืช เอากระเทียมกับพริกที่สับไว้ ลงไปผัด จนได้กลิ่น", false, 0)
//                1 -> OrderItem(i, "ก๋วยเตี๋ยว", 35.00, 2, 1, "http://utteasy.com/wp-content/uploads/2016/07/%E0%B8%81%E0%B9%8B%E0%B8%A7%E0%B8%A2%E0%B9%80%E0%B8%95%E0%B8%B5%E0%B9%8B%E0%B8%A2%E0%B8%A7%E0%B8%95%E0%B9%89%E0%B8%A1%E0%B8%A2%E0%B8%B3%E0%B9%84%E0%B8%82%E0%B9%88-%E0%B8%84%E0%B8%AD%E0%B8%A7%E0%B8%B1%E0%B8%87.jpg", "หมูเริ่มสุก ก็เหยาะน้ำปลาเพื่อปรุงรส ใส่ซีอิ๊วดำ ให้สีผัดกะเพราออกเข้มๆนิดๆ ดูน่ากิน ใส่น้ำตาลทราย ประมาณ ", false, 0)
//                2 -> OrderItem(i, "ขนมจีน", 30.00, 1, 1, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCiRDeDv9IQxeDnFWohMSloXRMKc6D1ESCDgY2dlJ2AccjbYpbNe5mOiy7", "ใส่ใบกะเพราลงไป คลุกแปปนึงก็ปิดไฟ ไม่งั้นเดี๋ยวกะเพราเหี่ยว", false, 0)
//                3 -> OrderItem(i, "เขียวหวาน", 45.00, 5, 1, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTIC8Yb8MdDZ_kqYZDF_eb30tBLepEeuP9Fswq4x5ufMmkCBo9fDAZNddazRA", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false, 0)
//                4 -> OrderItem(i, "ไก่ทอด", 15.00, 10, 1, "https://food.mthai.com/app/uploads/2017/07/Chicken-wings-fried-fish-sauce.jpg", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false, 0)
//                5 -> OrderItem(i, "เฟรนช์ฟรายส์", 30.00, 10, 1, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTopypPWpQzvWs0GkWMXRIh29M8scD9HOWuUBqWOPGXrW9dqJ9982I189wI9w", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false, 0)
//                else -> {
//                    OrderItem(i, "คะน้าหมูกรอบ", 35.00, 10, 1, "https://lh6.googleusercontent.com/proxy/7hobDadpgGkJemNVt9sL1yRJu2esM6_M1Qis4f8yAWbezV8ha66DYOQhLFuwFA7T3QK34mTiIJUEYuuvDTcLkNh36yQFzFmC8VneFg=w512-h288-nc", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false, 0)
//                }
//
//            }
//            orders.add(order!!)
//        }
//        callbackHttp.onSuccess(orders)

    }

    override fun updateAddedOrderToFirebase(orderItem: OrderItem) {

       // myRef?.child(orderItem._id.toString())?.child("isAdded")?.setValue(orderItem.isAddedInt)
        // myRef?.setValue(orderItem)


    }

}