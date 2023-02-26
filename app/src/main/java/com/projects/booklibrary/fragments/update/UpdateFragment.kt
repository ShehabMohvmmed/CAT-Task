package com.projects.booklibrary.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.projects.booklibrary.R
import com.projects.booklibrary.model.User
import com.projects.booklibrary.viemodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.update_firstName.setText(args.currentUser.firstName)
        view.update_lastName.setText(args.currentUser.lastName)
        view.update_age.setText(args.currentUser.age.toString())

        view.button_update.setOnClickListener {
            updateCurrentUser()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateCurrentUser() {
        val firstName = update_firstName.text.toString()
        val lastName = update_lastName.text.toString()
        val age = Integer.parseInt(update_age.text.toString())

        if(inputCheck(firstName, lastName, update_age.text)){
            // Create User Object
            val updatedUser = User(args.currentUser.id, firstName, lastName, Integer.parseInt(age.toString()))
            // Add Data to Database
            viewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userFirstName: String, userLastName: String, userAge: Editable):Boolean {
        return !(TextUtils.isEmpty(userFirstName) && TextUtils.isEmpty(userLastName) && userAge.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes"){_,_ ->
            viewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "Successfully deleted ${args.currentUser.firstName}",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}? ")
        builder.create().show()
    }

}