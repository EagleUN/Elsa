package un.eagle.elsa.graphql

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import org.jetbrains.annotations.NotNull
import un.eagle.elsa.Constants
import un.eagle.elsa.CreateUserMutation
import un.eagle.elsa.QueryAllUsersQuery


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

    //only a test method, not used
    fun fetchAllUsers() {
        Log.d(TAG, "fecthAllUsers")
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