package test.mts.ru.testcows

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_main.*
import test.mts.ru.testcoats.R
import test.mts.ru.testcows.dataflow.Cow
import test.mts.ru.testcows.dataflow.CowInteractor
import test.mts.ru.testcows.dataflow.CowListInterface
import test.mts.ru.testcows.dataflow.CowPresenter

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), CowListInterface.ViewInterface, CowListAdapter.ListListener {

    var presenter : CowListInterface.Presenter? = null
    var adapter: CowListAdapter? = null
    var interactor: CowListInterface.Interactor? = null

    override fun clickOn(id: Int) {
        presenter?.openCow(id)
    }

    override fun show(cows: List<Cow>) {
        adapter?.update(cows)
    }

    override fun open(cow: Cow) {
        val intent = Intent(this.context, CowActivity::class.java)
        intent.putExtra("COWINDEX", cow.Id)
        startActivity(intent)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val recicle = view.findViewById<RecyclerView>(R.id.recycle)
        val llm = LinearLayoutManager(this.activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        recicle.layoutManager = llm
        adapter = CowListAdapter(this)
        recicle.adapter = adapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            presenter?.create()
        }
        interactor = CowInteractor()
        presenter = CowPresenter(interactor!!, this)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter?.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        interactor?.onDestroy()
    }

}
