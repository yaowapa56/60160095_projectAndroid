package com.example.project_final


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import org.json.JSONObject
import com.google.firebase.database.FirebaseDatabase
/**
 * A simple [Fragment] subclass.
 */
class Recycler_data : Fragment() {

    var PhotoURL : String = ""
    var Name : String = ""

    fun newInstance(url: String,name : String): Recycler_data {

        val Recycler_data = Recycler_data()
        val bundle = Bundle()
        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        Recycler_data.setArguments(bundle)

        return Recycler_data
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_recycler_data, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        val login_button2 = view.findViewById(R.id.login_button2) as Button
        val add_btn = view.findViewById(R.id.adddata_btn) as Button

        val jsonString : String = loadJsonFromAsset("animate.json", activity!!.baseContext).toString()
        val json = JSONObject(jsonString)
        val jsonArray = json.getJSONArray("animate")

        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)

        val mRootRef = FirebaseDatabase.getInstance().getReference()
        val mUsersRef = mRootRef.child("animate_realtime")

        //ตั้งค่า Layout
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = layoutManager

        //ตั้งค่า Adapter
        val adapter = MyRecyclerAdapter(activity!!.baseContext,activity!!.supportFragmentManager,jsonArray)
        recyclerView.adapter = adapter


        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)

        tvName.setText(Name)

        login_button2.setOnClickListener{

            val builder = AlertDialog.Builder(this.context)
            builder.setMessage("ต้องการออกจากระบบใช่หรือไม่?")
            builder.setPositiveButton("ใช่") { dialog, id ->
                Toast.makeText(
                    this.context,
                    "ขอบคุณที่ใช้บริการค่ะ", Toast.LENGTH_SHORT
                ).show()
                LoginManager.getInstance().logOut()
                activity!!.supportFragmentManager.popBackStack()
            }
            builder.setNegativeButton("ไม่ใช่",
                DialogInterface.OnClickListener{ dialog, which ->
                    dialog.dismiss();
                })

            builder.show()
        }
        // Inflate the layout for this fragment

        add_btn.setOnClickListener {
            mUsersRef.child("Name").setValue("Yaowapa Pongpadcha")
            Toast.makeText(
                this.context,
                "Success!! You can add data", Toast.LENGTH_SHORT
            ).show()
        }
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()
            Name = bundle.getString("Name").toString()

        }

    }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }


}
