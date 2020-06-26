package com.cralos.introductionmvi.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cralos.introductionmvi.R
import com.cralos.introductionmvi.ui.DataStateListener
import com.cralos.introductionmvi.ui.main.state.MainStateEvent

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** */
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) }
            ?: throw Exception("Invalid activity")
        subscribeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()
            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "DEBUG: $context must implement DataStateListener.")
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            Log.e(TAG, "DEBUG: dataState -> $dataState")

            /**Loading and message*/
            dataStateHandler.onDataStateChange(dataState)


            /**HANDLE DATA<T>*/
            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogPosts ->
                        /**setBlog spots data*/
                        viewModel.setBlogListData(blogPosts)
                    }

                    mainViewState.user?.let { user ->
                        /**set user data*/
                        viewModel.setUser(user)
                    }
                }

            }

        })








        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let {
                Log.e(
                    TAG,
                    "debug: setting blog spots to recyclerView $viewState"
                )
            }

            viewState.user?.let { Log.e(TAG, "debug: setting user data $viewState") }

        })
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

}