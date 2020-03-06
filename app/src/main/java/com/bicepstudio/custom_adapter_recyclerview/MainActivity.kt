package com.bicepstudio.custom_adapter_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bicepstudio.custom_adapter_recyclerview.model.Account
import com.bicepstudio.custom_adapter_recyclerview.model.Menu
import com.bicepstudio.customadapter.adapter.CustomAdapter
import com.bicepstudio.customadapter.listener.OnCustomScroll
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_account.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

class MainActivity : AppCompatActivity() {

    private var isLoadMoreAccounts = true
    private var isLoadMoreMenus = true
    private val booleans = listOf(true, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menus = arrayListOf(
            Menu(android.R.drawable.ic_delete, "Delete"),
            Menu(android.R.drawable.ic_input_add, "Add"),
            Menu(android.R.drawable.ic_menu_search, "Search"),
            Menu(android.R.drawable.ic_delete, "Delete"),
            Menu(android.R.drawable.ic_input_add, "Add"),
            Menu(android.R.drawable.ic_menu_search, "Search"),
            Menu(android.R.drawable.ic_delete, "Delete"),
            Menu(android.R.drawable.ic_input_add, "Add"),
            Menu(android.R.drawable.ic_menu_search, "Search")
        )

        val accounts = arrayListOf(
            Account("blablabla", "blablabla@mail.com"),
            Account("lalalalal", "lalalalal@mail.com"),
            Account("heiheihei", "heiheihei@mail.com"),
            Account("hahahahah", "hahahahah@mail.com"),
            Account("wkwkwkwkw", "wkwkwkwkw@mail.com"),
            Account("hohohohoh", "hohohohoh@mail.com")
        )

        val menuAdapter = object : CustomAdapter<Menu>(R.layout.item_menu) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.itemView.ivMenu.setImageDrawable(getDrawable(this.list[position].icon))
                holder.itemView.tvMenu.text = this.list[position].name
            }
        }.also {
            it.list = menus
            it.notifyDataSetChanged()
        }

        rvMenu.adapter = menuAdapter
        //Default value loadMoreAt = 3
        rvMenu.layoutManager?.let {
            rvMenu.addOnScrollListener(object : OnCustomScroll(it, 2) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    if (isLoadMoreMenus) {
                        showLog("isLoadMoreMenus started")

                        menus.addAll(menus)

                        menuAdapter.apply {
                            list = menus
                            notifyDataSetChanged()
                        }

                        isLoadMoreMenus = booleans[(0..1).random()]
                    } else {
                        showLog("isLoadMoreMenus ended")
                    }
                }
            })
        }

        val accountAdapter = object : CustomAdapter<Account>(R.layout.item_account) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.itemView.tvUsername.text = this.list[position].username
                holder.itemView.tvEmail.text = this.list[position].email
            }
        }.also {
            it.list = accounts
            it.notifyDataSetChanged()
        }

        rvAccount.adapter = accountAdapter
        //Default value loadMoreAt = 3
        rvAccount.layoutManager?.let {
            rvAccount.addOnScrollListener(object : OnCustomScroll(it) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    if (isLoadMoreAccounts) {
                        showLog("isLoadMoreAccounts started")

                        accounts.addAll(accounts)

                        accountAdapter.apply {
                            list = accounts
                            notifyDataSetChanged()
                        }

                        isLoadMoreAccounts = booleans[(0..1).random()]
                    } else {
                        showLog("isLoadMoreAccounts ended")
                    }
                }
            })
        }
    }

    private fun showLog(msg: String) = Log.e("APP-LOG", msg)
}
