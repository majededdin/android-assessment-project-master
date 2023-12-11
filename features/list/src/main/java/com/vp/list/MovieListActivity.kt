package com.vp.list

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MovieListActivity : DaggerAppCompatActivity() {

    private var searchView: SearchView? = null
    private var searchViewExpanded = true
    private var searchTypedText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment(), ListFragment.TAG)
                .commit()
        } else {
            searchViewExpanded = savedInstanceState.getBoolean(IS_SEARCH_VIEW_ICONIFIED)
            searchTypedText = savedInstanceState.getString(SEARCH_TYPED_TEXT, "")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        searchView = menuItem.actionView as SearchView?
        searchView!!.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView!!.isIconified = searchViewExpanded
        searchView!!.setQuery(searchTypedText, false)
        searchView!!.clearFocus()
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val listFragment =
                    supportFragmentManager.findFragmentByTag(ListFragment.TAG) as ListFragment?
                listFragment!!.submitSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView!!.setOnCloseListener {
            val listFragment =
                supportFragmentManager.findFragmentByTag(ListFragment.TAG) as ListFragment?
            listFragment!!.submitSearchQuery("interview")
            false
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_SEARCH_VIEW_ICONIFIED, searchView!!.isIconified)
        outState.putString(SEARCH_TYPED_TEXT, searchView!!.query.toString())
    }

    override fun onStart() {
        super.onStart()
        clearFocus()
    }

    private fun clearFocus() {
        searchView?.clearFocus()
    }

    companion object {
        private const val IS_SEARCH_VIEW_ICONIFIED = "is_search_view_iconified"
        private const val SEARCH_TYPED_TEXT = "search_view_query"
    }

}