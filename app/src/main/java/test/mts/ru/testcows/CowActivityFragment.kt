package test.mts.ru.testcows

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import test.mts.ru.testcoats.R
import test.mts.ru.testcows.dataflow.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class CowActivityFragment : Fragment(), CowInterface.ViewInterface {


    var holder: SelectedHolder? = null
    var numberTxt : EditText? = null
    var breed: Spinner? = null
    var suit : Spinner? = null
    var date: TextView? = null

    var presenter: CowInterface.Presenter? = null
    var cow: Cow? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cow, container, false)
        numberTxt = view.findViewById(R.id.number)
        breed = view.findViewById(R.id.bread)
        suit = view.findViewById(R.id.suit)
        date = view.findViewById(R.id.date)

        val sadapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item,
                listOf(Suit.SuitOne, Suit.SuitTwo, Suit.SuitThree).map { it.name.capitalize() })
        suit?.adapter = sadapter

        val badapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item,
                listOf(Breed.BreedOne, Breed.BreedTwo, Breed.BreedThree).map { it.name.capitalize() })
        breed?.adapter = badapter


        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat)
            date?.text = sdf.format(cal.time)

        }

        date?.setOnClickListener {
            DatePickerDialog(this.context, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.findViewById<Button>(R.id.save).setOnClickListener {
                cow?.Id = numberTxt?.text?.toString() ?: ""
                val myFormat = "dd.MM.yyyy"
                val sdf = SimpleDateFormat(myFormat)
                try {
                    cow?.beath = sdf.parse(date?.text.toString()) ?: Date()
                }catch (e: Exception){
                    cow?.beath = Date()
                }

                cow?.saveBreed(Breed.from(breed?.selectedItemId?.toInt() ?: 0))
                cow?.saveSuit(Suit.from(suit?.selectedItemId?.toInt() ?: 0))
                cow?.let {
                    presenter?.check(it).let {
                        if (it!!) {
                            presenter?.save(cow!!)
                            this.activity?.finish()
                        }
                    }
                }
        }

        view.findViewById<Button>(R.id.close).setOnClickListener {
            this.activity?.finish()
        }
        presenter?.refresh()
        return view
    }

    override fun show(cow: Cow) {
        this.cow = cow
        numberTxt?.setText(cow.Id)
        breed?.setSelection(cow.getBreed().index())
        suit?.setSelection(cow.getSuit().index())
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat)
        date?.text = sdf.format(cow.beath)
    }

    override fun showAlert(text: String) {
        val builder = AlertDialog.Builder(this.activity)

        builder.setTitle("Cow app")
        builder.setMessage(text)
        builder.setPositiveButton("Ok") {dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        holder = context as? SelectedHolder

        holder?.let {
            presenter = CowShowPresenter(id = it.selected(), interactor = CowShowInteractor(), view = this)
        }
    }

    interface SelectedHolder {
        fun selected(): String
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}
