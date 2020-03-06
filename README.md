# Custom Adapter

[![](https://jitpack.io/v/abdur-rohman2883/custom-adapter.svg)](https://jitpack.io/#abdur-rohman2883/custom-adapter)

## What is custom adapter?
> Custom adapter is a tool that makes it easy for android programmers to make adapters on recyclerview without having to create multiple classes

## Setup

### Gradle

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.abdur-rohman2883:custom-adapter:1.0.0'
}
```

## Example

### data class Account
```kotlin
data class Account(val username: String, val email: String)
```

### data class Menu
```kotlin
data class Menu(val icon: Int, val name: String)
```

### item_account.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tool="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:contentPadding="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvUsername"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@android:color/black"
            android:textSize="20sp"
            android:textStyle="bold"
            tool:text="@tools:sample/full_names" />

        <TextView
            android:id="@+id/tvEmail"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginTop="8dp"
            tool:text="@tools:sample/lorem" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

### item_menu.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tool="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:contentPadding="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvUsername"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@android:color/black"
            android:textSize="20sp"
            android:textStyle="bold"
            tool:text="@tools:sample/full_names" />

        <TextView
            android:id="@+id/tvEmail"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginTop="8dp"
            tool:text="@tools:sample/lorem" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvMenu"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:layoutManager="androidx.recyclerview.widget.StaggeredGridLayoutManager"
        app:spanCount="2" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvAccount"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

</LinearLayout>
```

### MainActivity.kt
```kotlin
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
```