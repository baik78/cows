package test.mts.ru.testcows

import android.app.Application
import io.realm.Realm

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}