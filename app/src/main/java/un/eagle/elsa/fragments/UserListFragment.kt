package un.eagle.elsa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import un.eagle.elsa.R
import un.eagle.elsa.adapters.UsersAdapter

class UserListFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.UserListFragment"
    }

    fun getSampleUserList() : ArrayList<String> {
        val list = ArrayList<String>()
        list.addAll(arrayOf("Diego Niquefas", "Juan Moreno", "Laura Santos", "Christian Sanabria", "Sebastian Chaves"))
        list.addAll(arrayOf("Victor Ramirez", "Diego Caballero", "Alan Navarro", "Osman Jimenez", "Manuel Vergara"))
        list.addAll(arrayOf("Yoni el kpo", "Don Alberto", "Doña Belén", "Jose Niquefa", "Rafael Niquefa"))
        return list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_user_list,null)

        val postsView : RecyclerView = v.findViewById(R.id.userListRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val data = getSampleUserList()
        Log.d(TAG, "Users in userList: " + data.size )

        postsView.adapter = UsersAdapter(data)

        return v
    }
}