package com.cralos.introductionmvi.ui.main.state

import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null

) {}