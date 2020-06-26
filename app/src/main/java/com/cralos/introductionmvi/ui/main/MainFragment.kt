package com.cralos.introductionmvi.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cralos.introductionmvi.R
import com.cralos.introductionmvi.model.User
import com.cralos.introductionmvi.ui.DataStateListener
import com.cralos.introductionmvi.ui.main.state.MainStateEvent
import com.cralos.introductionmvi.util.TopSpacingItemDecorator
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_blog_list_item.*

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener
    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** */
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) }
            ?: throw Exception("Invalid activity")
        subscribeObservers()
        initRecyclerView()
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

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        val topSpacingItemDecorator = TopSpacingItemDecorator(30)
        recycler_view.addItemDecoration(topSpacingItemDecorator)
        mainRecyclerAdapter = MainRecyclerAdapter(requireContext())
        recycler_view.adapter = mainRecyclerAdapter
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
                Log.e(TAG, "debug: setting blog spots to recyclerView $viewState")
                mainRecyclerAdapter.submitList(it)
            }

            viewState.user?.let {user ->
                Log.e(TAG, "debug: setting user data $viewState")
                setUserPropertities(user)
            }

        })
    }

    private fun setUserPropertities(user: User) {
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(requireActivity())
                .load(user.image)
                .into(image)
        }
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

}