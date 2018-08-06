package test.mts.ru.testcows

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_cow.*
import test.mts.ru.testcoats.R

class CowActivity : AppCompatActivity(), CowActivityFragment.SelectedHolder {

    var selected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idx = intent.getStringExtra("COWINDEX")
        selected = idx

        setContentView(R.layout.activity_cow)
        setSupportActionBar(toolbar)



//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun selected(): String {
        return selected
    }


}
