package com.example.jetpacknavdrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_send.*

/**
 * A simple [Fragment] subclass.
 */
class SendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sendButton = buttonSend_Id
        sendButton.setOnClickListener {
            val action = SendFragmentDirections.actionNavSendIdToContactsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
