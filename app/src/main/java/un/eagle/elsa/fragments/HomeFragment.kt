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
import un.eagle.elsa.R
import un.eagle.elsa.WritePostActivity
import un.eagle.elsa.adapters.PostsAdapter
import un.eagle.elsa.data.model.Post

class HomeFragment : Fragment() {

    fun getSamplePosts() : ArrayList<Post> {
        val posts = ArrayList<Post>()

        posts.add(Post("Tomi Tomi", "Vamo a jugar, vamo a jugar, vamo a jugar"))
        posts.add(Post("Diego", "Arki too hard :'v"))
        posts.add(Post("Mater Chris", "zolo rails loka"))
        posts.add(Post("Laura", "rapunzel es mi princesa favorita"))
        posts.add(Post("Juan", "Moana to mama"))
        posts.add(Post("Chaves", "soy 100tifiko en gugol clau"))
        posts.add(Post("Chili", "Chillin"))
        posts.add(Post("Diego", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."))
        return posts
    }


    /**
     * Goes to a write post activity
     */
    private fun goToWritePostActivity() {
        activity?.let{
            val intent = Intent (it, WritePostActivity ::class.java)
            it.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_home,null)

        val postsView : RecyclerView = v.findViewById(R.id.postsRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val writePostButton : FloatingActionButton = v.findViewById(R.id.writePostButton)

        val data = getSamplePosts()

        val postsAdapter = PostsAdapter(data)
        postsView.adapter = postsAdapter

        writePostButton.setOnClickListener{ goToWritePostActivity() }

        return v
    }
}