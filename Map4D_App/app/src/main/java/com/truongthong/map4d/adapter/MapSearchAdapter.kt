package com.truongthong.map4d.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.truongthong.map4d.R
import com.truongthong.map4d.model.search.MapLocation
import java.util.*
import kotlin.collections.ArrayList

class MapSearchAdapter(
    var suggestList: MutableList<MapLocation>,
    var clickListener: OnMapItemClickListener
) :
    RecyclerView.Adapter<MapSearchAdapter.ViewHolder>(), Filterable {

    lateinit var suggestFilterList : MutableList<MapLocation>

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameLocal = itemView.findViewById(R.id.txt_name_suggest) as TextView
        var nameAddress = itemView.findViewById(R.id.txt_address_suggest) as TextView

        @SuppressLint("SetTextI18n")
        fun initialaze(suggestList: MapLocation, action: OnMapItemClickListener) {

            if (suggestList.name.length > 30) {
                nameLocal.text = suggestList.name.substring(0, 30) + "..."
            } else {
                nameLocal.text = suggestList.name
            }
            if (suggestList.address.length > 35) {
                nameAddress.text = suggestList.address.substring(0, 35) + "...."
            } else {
                nameAddress.text = suggestList.address
            }

            itemView.setOnClickListener {
                action.onItemClick(suggestList, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suggest, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val suggestItem = suggestList[position]

//        holder.itemView.apply {
//            if (suggestItem.name.length > 30) {
//                txt_name_suggest.text = suggestItem.name.substring(0, 30) + "..."
//            } else {
//                txt_name_suggest.text = suggestItem.name
//            }
//            if (suggestItem.address.length > 35) {
//                txt_address_suggest.text = suggestItem.address.substring(0, 35) + "...."
//            } else {
//                txt_address_suggest.text = suggestItem.address
//            }
//        }
        holder.initialaze(suggestList.get(position), clickListener)

    }

    override fun getItemCount(): Int {
        return suggestList.size
    }

    fun setLocationData(mapLocation: MutableList<MapLocation>?) {
        suggestList = mapLocation!!
        suggestFilterList = mapLocation
        notifyDataSetChanged()
    }

    //xu ly searchview
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    suggestFilterList = suggestList
                } else {
                    val resultList = ArrayList<MapLocation>()
                    for (row in suggestList) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    suggestFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = suggestFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                suggestFilterList = results?.values as MutableList<MapLocation>
                notifyDataSetChanged()
            }

        }
    }

    //interface xu ly su kien onclick item
    interface OnMapItemClickListener {
        fun onItemClick(suggestList: MapLocation, position: Int)
    }

//    private val onItemClickListener: ((MapLocation) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (MapLocation) -> Unit) {
//        onItemClickListener
//    }


}