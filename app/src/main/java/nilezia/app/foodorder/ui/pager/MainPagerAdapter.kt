package nilezia.app.foodorder.ui.pager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import nilezia.app.foodorder.ui.food.FoodProductFragment
import nilezia.app.foodorder.ui.friend.FriendsFragment
import nilezia.app.foodorder.ui.history.OrderHistoryFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val NUM_ITEMS = 3
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FoodProductFragment.newInstance()
        1 -> OrderHistoryFragment.newInstance()
        2 -> FriendsFragment.newInstance()
        else -> {
            FoodProductFragment.newInstance()
        }
    }

    override fun getCount(): Int = NUM_ITEMS

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "Order"
        1 -> "History"
        2 -> "Users"
        else -> {
            "Order"
        }

    }
}
