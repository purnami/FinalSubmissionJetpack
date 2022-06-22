package com.example.submission1.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.submission1.R
import com.example.submission1.utils.DataFilms
import com.example.submission1.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Test
import org.junit.Before

class HomeActivityTest {
    private val dataMovies= DataFilms.generateDataMovies()
    private val dataTvshow= DataFilms.generateDataTvshows()

    @Before
    fun setup(){
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataMovies.size))
    }

    @Test
    fun loadDetailMovies(){
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dataMovies[0].title)))
        onView(withId(R.id.text_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_release_date)).check(matches(withText(dataMovies[0].releaseDate)))
        onView(withId(R.id.text_category)).check(matches(isDisplayed()))
        onView(withId(R.id.text_category)).check(matches(withText(dataMovies[0].category)))
        onView(withId(R.id.text_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.text_duration)).check(matches(withText(dataMovies[0].duration)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dataMovies[0].description)))
    }

    @Test
    fun loadFavMovies(){
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.fav_btn1)).perform(click())
        onView(withId(R.id.rv_favmovies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favmovies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun loadTvshow(){
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataTvshow.size))
    }

    @Test
    fun loadDetailTvshow(){
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dataTvshow[0].title)))
        onView(withId(R.id.text_release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_release_year)).check(matches(withText(dataTvshow[0].releaseYear)))
        onView(withId(R.id.text_category)).check(matches(isDisplayed()))
        onView(withId(R.id.text_category)).check(matches(withText(dataTvshow[0].category)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dataTvshow[0].description)))
    }

    @Test
    fun loadFavTvshow(){
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.fav_btn2)).perform(click())
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_favtvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favtvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

}