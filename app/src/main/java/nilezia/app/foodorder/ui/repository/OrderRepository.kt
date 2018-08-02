package nilezia.app.foodorder.ui.repository

import android.content.Context
import nilezia.app.foodorder.model.OrderItem

class OrderRepository(context: Context) : OrderRepositoryContract {


    override fun getOrders(): MutableList<OrderItem>? {
        val orders = mutableListOf<OrderItem>()
        for (i in 0..6) {
            val order: OrderItem? = when (i) {
                0 -> OrderItem(i, "กระเพรา", 100.00, 0, 1, "http://www.rayonghip.com/wp-content/uploads/2017/03/Joke_Banlang-02.jpg", "บุบกระเทียม บุบพริกชี้ฟ้า(สับเฉียงๆ) และสับๆ ใส่น้ำมันพืช เอากระเทียมกับพริกที่สับไว้ ลงไปผัด จนได้กลิ่น", false)
                1 -> OrderItem(i, "ก๋วยเตี๋ยว", 120.00, 2, 1, "http://utteasy.com/wp-content/uploads/2016/07/%E0%B8%81%E0%B9%8B%E0%B8%A7%E0%B8%A2%E0%B9%80%E0%B8%95%E0%B8%B5%E0%B9%8B%E0%B8%A2%E0%B8%A7%E0%B8%95%E0%B9%89%E0%B8%A1%E0%B8%A2%E0%B8%B3%E0%B9%84%E0%B8%82%E0%B9%88-%E0%B8%84%E0%B8%AD%E0%B8%A7%E0%B8%B1%E0%B8%87.jpg", "หมูเริ่มสุก ก็เหยาะน้ำปลาเพื่อปรุงรส ใส่ซีอิ๊วดำ ให้สีผัดกะเพราออกเข้มๆนิดๆ ดูน่ากิน ใส่น้ำตาลทราย ประมาณ ", false)
                2 -> OrderItem(i, "ขนมจีน", 130.00, 1, 1, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCiRDeDv9IQxeDnFWohMSloXRMKc6D1ESCDgY2dlJ2AccjbYpbNe5mOiy7", "ใส่ใบกะเพราลงไป คลุกแปปนึงก็ปิดไฟ ไม่งั้นเดี๋ยวกะเพราเหี่ยว", false)
                3 -> OrderItem(i, "เขียวหวาน", 250.00, 5, 1, "https://www.khaosod.co.th/wp-content/uploads/2017/08/3-136.jpg", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false)
                else -> OrderItem(i, "คะน้าหมูกรอบ", 15.00, 10, 1, "https://www.khaosod.co.th/wp-content/uploads/2017/08/3-136.jpg", "จากนั้นก็ดาวไข่ ถ้าเป็นบ้านเรา บ้านเราใช้น้ำมันเยอะ ไฟแรงหน่อย พอน้ำมันเดือด ก็ตอกไข่ใส่ลงไป มันก็จะ", false)
            }
            orders.add(order!!)
        }
        return orders
    }


}