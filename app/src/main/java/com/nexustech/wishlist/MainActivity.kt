package com.nexustech.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemNameEditText: EditText
    private lateinit var itemPriceEditText: EditText
    private lateinit var storeNameEditText: EditText
    private lateinit var submitButton: MaterialButton

    private val itemList = mutableListOf<Item>()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupSubmitButton()
    }

    private fun initViews() {
        itemsRecyclerView = findViewById(R.id.itemsRv)
        itemNameEditText = findViewById(R.id.itemNameEditText)
        itemPriceEditText = findViewById(R.id.itemPriceEditText)
        storeNameEditText = findViewById(R.id.storeNameEditText)
        submitButton = findViewById(R.id.submitButton)
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter(itemList)
        itemsRecyclerView.adapter = adapter
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSubmitButton() {
        submitButton.setOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        val name = itemNameEditText.text.toString()
        val priceString = itemPriceEditText.text.toString()
        val storeName = storeNameEditText.text.toString()

        if (isInputValid(name, priceString, storeName)) {
            val price = priceString.toDoubleOrNull() // Attempt to convert to Double
            if (price != null) { // Check if conversion was successful
                val item = Item(name, priceString, storeName) // Keep price as string for display
                itemList.add(item)
                adapter.notifyItemInserted(itemList.size - 1)
                clearInputFields()
            } else {
                showToast("Please enter a valid price")
            }
        } else {
            showToast("Please fill in all fields")
        }
    }


    private fun isInputValid(name: String, price: String, storeName: String): Boolean {
        return name.isNotEmpty() && price.isNotEmpty() && storeName.isNotEmpty()
    }

    private fun clearInputFields() {
        itemNameEditText.text.clear()
        itemPriceEditText.text.clear()
        storeNameEditText.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
