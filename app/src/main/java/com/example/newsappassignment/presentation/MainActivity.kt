package com.example.newsappassignment.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappassignment.R
import com.example.newsappassignment.commonutils.Results
import com.example.newsappassignment.commonutils.isNotificationsEnabled
import com.example.newsappassignment.commonutils.showNotificationDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity for displaying news articles and providing sorting options.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val stringArray = arrayOf("Old news first", "New news first")

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var autoCompleteTextView: AutoCompleteTextView

    private lateinit var sortingAdapter: ArrayAdapter<String>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.recyclerView = findViewById(R.id.recycler)
        this.autoCompleteTextView = findViewById(R.id.autoCompleteTextView)

        this.recyclerView.layoutManager = LinearLayoutManager(this)
        this.sortingAdapter = ArrayAdapter(this, R.layout.sorting_item, stringArray)

        this.autoCompleteTextView.setAdapter(sortingAdapter)

        if (!isNotificationsEnabled(this)) {
            showNotificationDialog(this)
        } else {
            getFirebaseToken()
        }
        getNews()
    }

    /**
     * Retrieves news articles from the ViewModel and sets up sorting options.
     */
    private fun getNews() {
        lifecycleScope.launch {
            viewModel.state.collect { result ->
                when (result) {
                    is Results.Success -> {
                        val newsAdapter =
                            NewsAdapter(result.data.sortedByDescending { it.publishedAt })
                        recyclerView.adapter = newsAdapter
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, position, _ ->
                                when (position) {
                                    0 -> {
                                        newsAdapter.sortDataSet(0)
                                    }

                                    1 -> {
                                        newsAdapter.sortDataSet(1)
                                    }
                                }
                            }
                    }

                    is Results.Failure -> {
                        Toast.makeText(
                            this@MainActivity, "Something went wrong",
                            LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * Retrieves the Firebase Cloud Messaging (FCM) token.
     * This token is used to identify the device and is required for sending push notifications.
     * If the token retrieval fails, an error message is logged.
     */
    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM Token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                val token = task.result
                Log.d("FCM Token", token.toString())
            }
        })
    }
}