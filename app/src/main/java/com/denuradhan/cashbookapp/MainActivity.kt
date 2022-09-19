package com.denuradhan.cashbookapp


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*
import kotlin.collections.ArrayList
import com.denuradhan.cashbookapp.adapter.AdapterRV
import com.denuradhan.cashbookapp.helper.Helper

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val extra_username = "username"
    }

    var username = "username"

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_tambahPemasukan : ImageButton = findViewById(R.id.btn_tambahPemasukan)
        val btn_tambahPengeluaran : ImageButton = findViewById(R.id.btn_tambahPengeluaran)
        val btn_detailCashFlow : ImageButton = findViewById(R.id.btn_detailCashFlow)
        val btn_Pengaturan : ImageButton = findViewById(R.id.btn_pengaturan)

        val tv_pengeluaran : TextView = findViewById(R.id.tv_pengeluaran)
        val tv_pemasukan : TextView = findViewById(R.id.tv_pemasukan)

        //dump
//        val tv_username : TextView = findViewById(R.id.tv_username)
        username = intent.getStringExtra(extra_username).toString()
//        tv_username.setText(username)

        val swipeRefreshLayout : SwipeRefreshLayout = findViewById(R.id.container)

        val lineChart : LineChart = findViewById(R.id.lineChart)

        btn_tambahPemasukan.setOnClickListener(this)
        btn_tambahPengeluaran.setOnClickListener(this)
        btn_detailCashFlow.setOnClickListener(this)
        btn_Pengaturan.setOnClickListener(this)

        val nominal_pemasukan = ArrayList<Int>()
        val nominal_pengeluaran = ArrayList<Int>()
        val tanggal = ArrayList<Int>()

        val pemasukan = ArrayList<Entry>()
        val pengeluaran = ArrayList<Entry>()

        //read sqlide data
        val db = Helper(this, null)

        val cursor_pemasukan = db.getPemasukan()
        val cursor_pengeluaran = db.getPengeluaran()
        val cursor_tanggal = db.getUang()

        cursor_pemasukan!!.moveToFirst()
        cursor_pengeluaran!!.moveToFirst()
        cursor_tanggal!!.moveToFirst()

        if (cursor_pemasukan.moveToFirst()){
            nominal_pemasukan += (cursor_pemasukan.getInt(cursor_pemasukan.getColumnIndex(Helper.NOMINAL_COL)))

            while(cursor_pemasukan.moveToNext()){
                nominal_pemasukan += (cursor_pemasukan.getInt(cursor_pemasukan.getColumnIndex(Helper.NOMINAL_COL)))
            }


        }
        if (cursor_pengeluaran.moveToFirst()){
            nominal_pengeluaran += (cursor_pengeluaran.getInt(cursor_pengeluaran.getColumnIndex(Helper.NOMINAL_COL)))

            while (cursor_pengeluaran.moveToNext()){
                nominal_pengeluaran += (cursor_pengeluaran.getInt(cursor_pengeluaran.getColumnIndex(Helper.NOMINAL_COL)))
            }
        }

        if (cursor_tanggal.moveToFirst()){
            tanggal += (cursor_tanggal.getInt(cursor_tanggal.getColumnIndex(Helper.TANGGAL_COl)))

            while (cursor_pengeluaran.moveToNext()){
                tanggal += (cursor_tanggal.getInt(cursor_tanggal.getColumnIndex(Helper.TANGGAL_COl)))
            }
        }



        for (i in nominal_pemasukan.indices){
            pemasukan.add(Entry((i).toFloat(), (nominal_pemasukan.elementAt(i).toFloat())))
        }

        for (i in nominal_pengeluaran.indices){
            pengeluaran.add(Entry((i).toFloat(), (nominal_pengeluaran.elementAt(i).toFloat())))
        }


        val pemasukanLineDataSet = LineDataSet(pemasukan, "Pemasukan")
        pemasukanLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pemasukanLineDataSet.color = Color.GREEN
        pemasukanLineDataSet.circleRadius = 5f
        pemasukanLineDataSet.setCircleColor(Color.GREEN)

        val pengeluaranLineDataSet = LineDataSet(pengeluaran, "Pengeluaran")
        pengeluaranLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pengeluaranLineDataSet.color = Color.RED
        pengeluaranLineDataSet.circleRadius = 5f
        pengeluaranLineDataSet.setCircleColor(Color.RED)

        lineChart.description.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.data = LineData(pemasukanLineDataSet, pengeluaranLineDataSet)
        lineChart.animateXY(100, 500)


        tv_pemasukan.text = nominal_pemasukan.sum().toString()
        tv_pengeluaran.text = nominal_pengeluaran.sum().toString()

        swipeRefreshLayout.setOnRefreshListener {

            val mIntent = intent
            finish()
            overridePendingTransition(0, 0)
            startActivity(mIntent)
            overridePendingTransition(0, 0)

            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.btn_tambahPemasukan ->{
                val intentTambahPemasukan = Intent(this@MainActivity, TambahPemasukanActivity::class.java)
                intentTambahPemasukan.putExtra(TambahPemasukanActivity.extra_username, username)
                startActivity(intentTambahPemasukan)
                finish()
            }
            R.id.btn_tambahPengeluaran ->{
                val intentTambahPengeluaran = Intent(this@MainActivity, TambahPengeluaranActivity::class.java)
                intentTambahPengeluaran.putExtra(TambahPengeluaranActivity.extra_username, username)
                startActivity(intentTambahPengeluaran)
                finish()
            }
            R.id.btn_detailCashFlow ->{
                val intentDetailCashFlow = Intent(this@MainActivity, DetailCashFlowActivity::class.java)
                intentDetailCashFlow.putExtra(DetailCashFlowActivity.extra_username, username)
                startActivity(intentDetailCashFlow)
                finish()
            }
            R.id.btn_pengaturan ->{
                val intentPengaturan = Intent(this@MainActivity, PengaturanActivity::class.java)
                intentPengaturan.putExtra(PengaturanActivity.extra_username, username)
                startActivity(intentPengaturan)
                finish()
            }

        }
    }


}