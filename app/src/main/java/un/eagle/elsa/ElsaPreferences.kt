package un.eagle.elsa

import android.content.Context

object ElsaPreferences {

    fun setUserId(context: Context, userId: String) {
        context.getSharedPreferences(Constants.APP_PACKAGE, Context.MODE_PRIVATE).edit().putString(Constants.Preferences.USER_ID, userId).commit()
    }

    fun getUserId(context: Context) : String {
        val preferences = context.getSharedPreferences(Constants.APP_PACKAGE, Context.MODE_PRIVATE)
        return preferences.getString(Constants.Preferences.USER_ID, "")!!
    }

    fun setSessionJwt(context: Context, jwt: String) {
        context.getSharedPreferences(Constants.APP_PACKAGE, Context.MODE_PRIVATE).edit().putString(Constants.Preferences.JWT_TOKEN, jwt).commit()

    }

    fun getSessionJwt(context: Context): String {
        val preferences = context.getSharedPreferences(Constants.APP_PACKAGE, Context.MODE_PRIVATE)
        return preferences.getString(Constants.Preferences.JWT_TOKEN, "")!!

    }

    fun deleteUserId(context: Context) {
        setUserId(context, "")
    }

    fun deleteSessionJwt(context: Context) {
        setSessionJwt(context, "")
    }
}