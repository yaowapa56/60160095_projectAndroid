package com.example.project_final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class Detail_data : Fragment() {

    private var Title : String = ""
    private var Description : String = ""
    private var Vote : String = ""
    private var Img : String = ""

    fun newInstance(title: String,description: String,vote: String,imag: String): Detail_data {

        val fragment = Detail_data()
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("description", description)
        bundle.putString("vote", vote)
        bundle.putString("image", imag)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            Title = bundle.getString("title").toString()
            Description = bundle.getString("description").toString()
            Vote = bundle.getString("vote").toString()
            Img = bundle.getString("image").toString()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_data, container, false)
        val imgVi : ImageView = view.findViewById(R.id.about_image)
        val titleTxt : TextView = view.findViewById(R.id.about_firstname)
        val descripTxt : TextView = view.findViewById(R.id.about_lastname)
        val voteTxt : TextView = view.findViewById(R.id.about_position)



        Glide.with(activity!!.baseContext)
            .load(Img)
            .into(imgVi)

        titleTxt.setText(Title)
        descripTxt.setText(Description)
        voteTxt.setText(Vote)


        // Inflate the layout for this fragment

        return view
    }


}
