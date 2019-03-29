package nilezia.app.foodorder.ui.friend

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_friends.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.ui.friend.adapter.FriendsAdapter
import nilezia.app.foodorder.ui.repository.UserRepository

class FriendsFragment : BaseMvpFragment<FriendsContract.View, FriendsContract.Presenter>(), FriendsContract.View {


    private lateinit var mAdapter: FriendsAdapter

    companion object {
        fun newInstance() = FriendsFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setupLayout(): Int = R.layout.fragment_friends

    override fun setupView() {

    }

    override fun setupInstance() {

        setupRecyclerView()
        setupAdapter()
        setupSwiftRefresh()
        getPresenter().registerRepository(UserRepository())
        getPresenter().requestUsers()
    }

    private fun setupRecyclerView() {
        mAdapter = FriendsAdapter()

    }

    private fun setupAdapter() {

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
                    .apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
            adapter = mAdapter
        }

    }

    override fun bindView(view: View) {

    }

    override fun createPresenter(): FriendsContract.Presenter = FriendsPresenter.create()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun onUpdateUsersList(items: MutableList<UserInfo>) {
        swipeRefresh.isRefreshing = false
        mAdapter.users = items
        mAdapter.notifyDataSetChanged()

    }

    private fun setupSwiftRefresh() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            getPresenter().requestUsers()
        }
    }

}