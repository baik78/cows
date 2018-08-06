package test.mts.ru.testcows

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmList
import test.mts.ru.testcoats.R
import test.mts.ru.testcows.dataflow.Cow
import java.text.SimpleDateFormat

class CoatHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var number: TextView? = null
    var info: TextView? = null
    var view: View? = null

    init {
        view = itemView
        itemView?.apply {
            number = findViewById(R.id.number)
            info = findViewById(R.id.info)
        }
    }

    fun apply(cow: Cow, color: Boolean) {
        number?.text = "# ${cow.Id}"
        info?.text = "Порода: ${ cow.getBreed() } Масть: ${cow.getSuit()}. Возраст: ${cow.ages()}"
//        if(color)
//            view?.setBackgroundColor(R.color.blue)
//        else
//            view?.setBackgroundColor(R.color.whiteColor)
    }
}

class CowListAdapter(val listener: ListListener) : RecyclerView.Adapter<CoatHolder>() {
    interface ListListener {
        fun clickOn(id: Int)
    }

    var items: List<Cow> = listOf()

    fun update(its : List<Cow>) {
        items = its
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_line, parent, false)
        return CoatHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CoatHolder, position: Int) {
        val cow = items[position]
        cow.let {
            if(position%2 == 0)
                holder.apply(it, true)
            else
                holder.apply(it, false)
        }
        holder.itemView.setOnClickListener {
            listener.clickOn(position)
        }
    }

}