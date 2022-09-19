package com.denuradhan.cashbookapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.denuradhan.cashbookapp.R

class AdapterRV(private val status: ArrayList<String>, private val tanggal: ArrayList<String>, private val nomial: ArrayList<Int>, private val keterangan: ArrayList<String>, private val img: ArrayList<String>) :
    RecyclerView.Adapter<AdapterRV.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var status : TextView
        var tanggal : TextView
        var nominal : TextView
        var keterangan : TextView
        var img : ImageView

        init {
            // Define click listener for the ViewHolder's View.
            status = view.findViewById(R.id.list_status)
            tanggal = view.findViewById(R.id.list_tanggal)
            nominal = view.findViewById(R.id.list_nominal)
            keterangan = view.findViewById(R.id.list_keterangan)
            img = view.findViewById(R.id.list_img)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.status.text = status[position]
        viewHolder.tanggal.text = tanggal[position]
        viewHolder.nominal.text = nomial[position].toString()
        viewHolder.keterangan.text = keterangan[position]

        val str1 = "in"
        val str2 = "out"

        if (img[position].trim().equals(str1)){
            viewHolder.img.setImageResource(R.drawable.`in`)
        }
        else if (img[position].trim().equals(str2)){
            viewHolder.img.setImageResource(R.drawable.out)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tanggal.size

}
