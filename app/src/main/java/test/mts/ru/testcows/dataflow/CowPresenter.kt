package test.mts.ru.testcows.dataflow

import android.os.Parcel
import android.os.Parcelable

class CowPresenter(val interactor : CowListInterface.Interactor, val view: CowListInterface.ViewInterface) : CowListInterface.Presenter {


    override fun refresh() {
        interactor.getCows().orEmpty().let {
            view.show(it)
        }
    }

    override fun create() {
        interactor.new().let {
            view.open(it)
        }
    }

    override fun openCow(index: Int) {
        interactor.getCows().orEmpty().let {
            val cow = it[index]
            if(cow != null ) {view.open(cow)}
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

}