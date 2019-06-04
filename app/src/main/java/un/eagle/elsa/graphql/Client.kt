package un.eagle.elsa.graphql

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import org.jetbrains.annotations.NotNull
import un.eagle.elsa.*


object Client
{
    const val TAG = "Eagle.Client"
    const val URL = Constants.GRAPHQL_URL

    val apollo: ApolloClient = reset()

    fun reset(
        //token: String = BuildConfig.CONTENTFUL_DELIVERY_TOKEN
    ): ApolloClient {
        val httpClient: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        //addHeader( "Authorization",  "Bearer $token" )
                        .build()
                )
            }
            .build()

        return ApolloClient
            .builder()
            .serverUrl(URL)
            .okHttpClient(httpClient)
            .build()
    }

    fun createNewUser(
        name: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        callback: ApolloCall.Callback<CreateUserMutation.Data> ) {
        Log.d(TAG, "createNewUser")
        apollo.mutate(
            CreateUserMutation.builder()
                .name(name)
                .last_name(lastName)
                .password(password)
                .password_confirmation(passwordConfirmation)
                .email(email)
                .build()
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
        callback: ApolloCall.Callback<QueryHomeFeedForUser.Data>
    ) {
        Log.d(TAG, "getHomeFeedFor($userId)")
        apollo.query(
            QueryHomeFeedForUser.builder().id(userId).build()
        ).enqueue ( callback )
    }

    fun getProfileFeedFor(
        userId: String,
        callback: ApolloCall.Callback<QueryHomeFeedForUser.Data>
    ) {
        Log.d(TAG, "getHomeFeedFor($userId)")
        apollo.query(
            QueryHomeFeedForUser.builder().id(userId).build()
        ).enqueue ( callback )
    }


    fun getFollowingFor(
        userId: String,
        callback : ApolloCall.Callback<QueryFollowing.Data>
    ) {
        Log.d(TAG, "getFollowersFor($userId)")
        apollo.query(
            QueryFollowing.builder().userId(userId).build()
        ).enqueue(callback)
    }

    fun getFollowersFor(
        userId: String,
        callback : ApolloCall.Callback<QueryFollowers.Data>
    )  {
        Log.d(TAG, "getFollowersFor($userId)")
        apollo.query(
            QueryFollowers.builder().userId(userId).build()
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