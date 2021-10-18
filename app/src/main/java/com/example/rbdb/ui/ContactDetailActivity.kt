package com.example.rbdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.ActivityContactDetailBinding
import com.example.rbdb.ui.arch.AppViewModel



class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    private val viewModel: AppViewModel by viewModels()
    private var contactId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.cDetailTopAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.init(AppDatabase.getDatabase(this))

        contactId = intent.getLongExtra("contact_id", -1)

        val observer = Observer<CardEntity> { card ->
            val contactName = card.name
            val avatar = createAvatar(contactName)
            binding.contactPhoto.setImageDrawable(avatar)
            binding.contactName.text = contactName
            binding.tvCompany.text = card.business
            binding.phoneNumber.text = card.phone
            binding.email.text = card.email
            binding.description.text = card.description

        }
        viewModel.getCardById(contactId).observe(this,observer)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.contact_detail_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        //Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show()
        finish()
        //startActivity(Intent(this, MainActivity::class.java))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit_menu_item -> {
            //Toast.makeText(this, "edit pressed", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra(CONTACT_ID, contactId)
            startActivity(intent)
            true
        }

        R.id.delete_menu_item -> {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.confirm_delete_contact)
            builder.setPositiveButton("Delete") { _, _ ->
                deleteContact(contactId)
                // Return to Homepage
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

                finish()
            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.warningRed))

            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val observer = Observer<CardEntity> { card ->
            val contactName = card.name
            val avatar = contactName?.let { createAvatar(it) }
            binding.contactPhoto.setImageDrawable(avatar)
            binding.contactName.text = contactName
            binding.tvCompany.text = card.business
            binding.phoneNumber.text = card.phone
            binding.email.text = card.email
            binding.description.text = card.description

        }
        viewModel.getCardById(contactId).observe(this,observer)
    }

    private fun createAvatar(name: String): TextDrawable {
        val firstInitial = name[0].toString()
        val whiteSpaceIndex = name.indexOf(" ")
        val secondInitial = name[whiteSpaceIndex + 1].toString()
        val initials = firstInitial + secondInitial
        val generator: ColorGenerator = ColorGenerator.MATERIAL

        return TextDrawable.builder().buildRound(initials, generator.getColor(secondInitial))
    }

    private fun deleteContact(contactId: Long) {
        Log.d("contactId to be deleted", contactId.toString())
        viewModel.deleteCardAndCrossRefByCardId(contactId)
    }

    companion object {
        const val CONTACT_ID = "contact id"
    }
}