package un.eagle.elsa.graphql

import com.apollographql.apollo.ApolloClient
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import okhttp3.OkHttpClient
import org.jetbrains.annotations.NotNull
import un.eagle.elsa.*


object Client
{
    const val TAG = "Eagle.Client"
    const val URL = Constants.GRAPHQL_URL

    private var apollo: ApolloClient = reset("")

    private fun addBearer(token: String): String {
        return "Bearer $token"
    }


    fun reset(token: String): ApolloClient {
        Log.d(TAG, "SESSION TOKEN RESET TO: $token" )
        val httpClient: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader( Constants.Api.Headers.AUTH,  addBearer(token) )
                        .build()
                )
            }
            .build()

        apollo = ApolloClient
            .builder()
            .serverUrl(URL)
            .okHttpClient(httpClient)
            .build()
        return apollo
    }

    fun createNewUser(
        name: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        passwordConfirmation: String,
        callback: ApolloCall.Callback<CreateUserMutation.Data> ) {
        Log.d(TAG, "createNewUser")
        apollo.mutate(
            CreateUserMutation.builder()
                .name(name)
                .last_name(lastName)
                .password(password)
                .username(username)
                .password_confirmation(passwordConfirmation)
                .email(email)
                .build()
        ).enqueue(callback)
    }

    fun queryUserById(
        userId: String,
        callback: ApolloCall.Callback<UserByIdQuery.Data> ) {
        Log.d(TAG, "queryUserById($userId)")
        apollo.query(
            UserByIdQuery.builder().idUser(userId).build()
        ).enqueue(callback)
    }


    fun createUserSession(
        email: String,
        password: String,
        callback: ApolloCall.Callback<CreateNewUserSessionMutation.Data>
    ) {
        Log.d(TAG, "createUserSession")
        apollo.mutate(
            CreateNewUserSessionMutation.builder().email(email).password(password).build()
        ).enqueue( callback )
    }

    fun getUserListFor(
        userId: String,
        callback : ApolloCall.Callback<UserListQuery.Data>
    ) {
        Log.d(TAG, "getUserListFor")
        apollo.query(
            UserListQuery.builder().userId(userId).build()
        ).enqueue ( callback )
    }

    fun getHomeFeedFor(
        userId: String,
        callback: ApolloCall.Callback<HomeFeedForUserQuery.Data>
    ) {
        Log.d(TAG, "getHomeFeedFor($userId)")
        apollo.query(
            HomeFeedForUserQuery.builder().id(userId).build()
        ).enqueue ( callback )
    }

    fun getProfileFeedFor(
        userId: String,
        callback: ApolloCall.Callback<ProfileFeedForUserQuery.Data>
    ) {
        Log.d(TAG, "getProfileFeedFor($userId)")
        apollo.query(
            ProfileFeedForUserQuery.builder().id(userId).build()
        ).enqueue ( callback )
    }


    fun queryFollowingFor(
        userId: String,
        callback : ApolloCall.Callback<FollowingQuery.Data>
    ) {
        Log.d(TAG, "queryFollowersFor($userId)")
        apollo.query(
            FollowingQuery.builder().userId(userId).build()
        ).enqueue(callback)
    }

    fun addToken(
        userId: String,
        token: String,
        callback: ApolloCall.Callback<AddTokenMutation.Data>
    ) {
        Log.d(TAG, "addToken $token for user $userId")
        apollo.mutate(
            AddTokenMutation.builder().userId(userId).token(token).build()
        ).enqueue(callback)
    }

    fun queryFollowersFor(
        userId: String,
        callback : ApolloCall.Callback<FollowersQuery.Data>
    )  {
        Log.d(TAG, "queryFollowersFor($userId)")
        apollo.query(
            FollowersQuery.builder().userId(userId).build()
        ).enqueue(callback)
    }

    fun createPost(
        userId: String,
        content: String,
        callback: ApolloCall.Callback<CreatePostMutation.Data>
    ) {
        Log.d(TAG, "createPost($userId,$content)")
        apollo.mutate(
            CreatePostMutation.builder().userId(userId).content(content).build()
        ).enqueue(callback)
    }

    fun createFollow(
        followerId: String,
        followingId: String,
        callback: ApolloCall.Callback<CreateFollowMutation.Data>
    ) {
        Log.d(TAG, "createFollow($followerId,$followingId)")
        apollo.mutate(
            CreateFollowMutation.builder().followerId(followerId).followingId(followingId).build()
        ).enqueue(callback)
    }

    fun deleteFollow(
        followerId: String,
        followingId: String,
        callback: ApolloCall.Callback<DeleteFollowMutation.Data>
    ) {
        Log.d(TAG, "createFollow($followerId,$followingId)")
        apollo.mutate(
            DeleteFollowMutation.builder().followerId(followerId).followingId(followingId).build()
        ).enqueue(callback)
    }

    fun queryFollow(
        followerId: String,
        followingId: String,
        callback: ApolloCall.Callback<FollowsQuery.Data>
    ) {
        Log.d(TAG, "queryFollows($followerId, $followingId)")
        apollo.query(
            FollowsQuery.builder().followerId(followerId).followingId(followingId).build()
        ).enqueue(callback)
    }

    fun createShare(
        userId: String,
        postId: String,
        callback: ApolloCall.Callback<CreateShareMutation.Data>
    ) {
        Log.d(TAG, "createSahre(user=$userId, post=$postId)")
        apollo.mutate(
            CreateShareMutation.builder().userId(userId).postId(postId).build()
        ).enqueue(callback)
    }

    fun queryNotificationsFor(
        userId: String,
        callback: ApolloCall.Callback<NotificationsForUserQuery.Data>
    ) {
        Log.d(TAG, "queryNotificationsForUser($userId)")
        apollo.query(
            NotificationsForUserQuery.builder().userId(userId).build()
        ).enqueue(callback)
    }

    fun getPlaylist(
        id: String,
        callback: ApolloCall.Callback<GetMusicListQuery.Data>) {
        Log.d(TAG, "getPlaylist")
        apollo.query(
            GetMusicListQuery.builder().id(id).build()
        ).enqueue(callback)
    }

    //only a test method, not used
    fun getAllUsers() {
        Log.d(TAG, "getAllUsers")
        apollo.query(
            QueryAllUsersQuery.builder().build()
        ).enqueue( object : ApolloCall.Callback<QueryAllUsersQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.e(TAG, e.message, e)
            }

            override fun onResponse(@NotNull dataResponse: Response<QueryAllUsersQuery.Data>) {

                Log.d(TAG, dataResponse.data().toString())

                val data  = dataResponse.data()?.allUsers()
                Log.d(TAG, "Total: " + data?.total() )

                data?.list()?.forEach {
                    Log.d(TAG, "User: " + it.name() + " " + it.last_name() )
                }

            }
        })
    }
}