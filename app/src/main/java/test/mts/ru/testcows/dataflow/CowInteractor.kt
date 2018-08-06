package test.mts.ru.testcows.dataflow

import io.realm.Realm
import io.realm.RealmResults

class CowInteractor : CowListInterface.Interactor {

    private var realm : Realm? = null

    init {
        realm = Realm.getDefaultInstance()
    }

    override fun new(): Cow {
        return Cow()
    }

    override fun onDestroy() {
        realm?.close()
        realm = null
    }

    override fun getCows(): RealmResults<Cow>? {
        return  realm?.where(Cow::class.java)?.findAll()
    }

    override fun update(cow: Cow) {

    }

}