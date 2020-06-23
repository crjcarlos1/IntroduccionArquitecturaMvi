package com.cralos.introductionmvi.ui.main.state

import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User

data class MainViewState(

    val blogPosts: List<BlogPost>? = null,
    val user: User? = null

) {}