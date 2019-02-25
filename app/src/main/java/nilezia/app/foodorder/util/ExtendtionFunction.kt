package nilezia.app.foodorder.util

import java.text.SimpleDateFormat
import java.util.*


fun Date.format(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}
