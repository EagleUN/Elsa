package un.eagle.elsa.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import un.eagle.elsa.MockData
import un.eagle.elsa.R
import un.eagle.elsa.activities.WritePostActivity
import un.eagle.elsa.adapters.PostsAdapter
import un.eagle.elsa.data.model.Post

class HomeFragment : Fragment() {


    /**
     * Goes to a write post activity
     */
    private fun goToWritePostActivity() {
        activity?.let{
            val intent = Intent (it, WritePostActivity::class.java)
            it.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_home,null)
        activity?.title = getString(R.string.title_home)

        val postsView : RecyclerView = v.findViewById(R.id.postsRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val writePostButton : FloatingActionButton = v.findViewById(R.id.writePostButton)

        val data = MockData.posts()

        val postsAdapter = PostsAdapter(data)
        postsView.adapter = postsAdapter

        writePostButton.setOnClickListener{ goToWritePostActivity() }

        return v
    }
}