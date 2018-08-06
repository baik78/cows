package test.mts.ru.testcows.dataflow

import io.realm.RealmList
import io.realm.RealmResults

interface CowListInterface {
    interface Presenter {
        fun refresh()
        fun openCow(index: Int)
        fun create()
        fun onDestroy()
    }

    interface Interactor {
        fun getCows() : RealmResults<Cow>?
        fun new() : Cow
        fun update(cow: Cow)
        fun onDestroy()
    }

    interface ViewInterface {
        fun show(cows: List<Cow>)
        fun open(cow: Cow)
        fun showLoading()
        fun hideLoading()
    }
}

interface CowInterface {
    interface Presenter {
        fun refresh()
        fun save(cow: Cow)
        fun check(cow: Cow) : Boolean
        fun onDestroy()
    }

    interface Interactor {
        fun cow(id: String) : Cow?
        fun save(cow: Cow)
        fun onDestroy()
    }

    interface ViewInterface {
        fun show(cow: Cow)
        fun showAlert(text: String)
    }
}