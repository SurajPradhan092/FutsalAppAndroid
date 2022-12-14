package com.example.futsalapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.futsalapp.FutsaldetailActivity
import com.example.futsalapp.R
import com.example.futsalapp.UserupdateActivity
import com.example.futsalapp.api.ServiceBuilder
import com.example.futsalapp.model.Player
import com.example.futsalapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserDetailFragment : Fragment() {

    private lateinit var tvname: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvDOB: TextView
    private lateinit var tvwin: TextView
    private lateinit var loss: TextView
    private lateinit var btnUpdate: Button
    var player: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_detail, container, false)
        tvname = view.findViewById(R.id.tvname)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvPhone = view.findViewById(R.id.tvPhone)
        tvDOB = view.findViewById(R.id.tvDOB)
        tvwin = view.findViewById(R.id.tvwin)
        loss = view.findViewById(R.id.loss)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        btnUpdate.setOnClickListener {
            val intent = Intent (context, UserupdateActivity::class.java)
                    .putExtra("user", player)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userrepo = UserRepository()
                val response = userrepo.getUser()
                val success = response.success
                if (success == true){
                    val userdata = response.data!!

                    withContext(Dispatchers.Main){
                        tvname.text = userdata.fname +" "+ userdata.lname
                        tvEmail.text = userdata.email
                        tvPhone.text = userdata.phone
                        tvDOB.text = userdata.age
                        tvwin.text = userdata.tvwin
                        loss.text = userdata.loss
                        player = userdata

                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error: ${e.toString()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return view
    }




}