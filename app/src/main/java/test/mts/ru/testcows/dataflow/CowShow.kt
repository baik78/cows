package test.mts.ru.testcows.dataflow

import io.realm.Realm

class CowShowInteractor : CowInterface.Interactor {

    private var realm : Realm? = null

    init {
        realm = Realm.getDefaultInstance()
    }

    override fun cow(id: String): Cow? {
        if (id == "") {
            return Cow()
        }else{
            val cow = realm?.where(Cow::class.java)?.equalTo("Id", id)?.findFirst()
            cow?.let {
                val res = realm?.copyFromRealm(it)
                return  res
            }
            return null
        }
    }

    override fun save(cow: Cow) {
        realm?.executeTransaction {
            realm?.copyToRealmOrUpdate(cow)
        }
    }

    override fun onDestroy() {
        realm?.close()
        realm = null
    }

}

class CowShowPresenter(val id: String, val interactor: CowInterface.Interactor?, val view: CowInterface.ViewInterface?) : CowInterface.Presenter {
    override fun refresh() {
        val cow = interactor?.cow(id)
        cow?.let {
            view?.show(it)
        }
    }

    override fun save(cow: Cow) {
        interactor?.save(cow)
    }

    override fun check(cow: Cow): Boolean =
        if (cow.Id.isEmpty()) {
            view?.showAlert("Номер не может быть пустым")
            false
        }else{
            true
        }


    override fun onDestroy() {
        interactor?.onDestroy()
    }

}