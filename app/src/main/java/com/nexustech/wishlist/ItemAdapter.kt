package com.nexustech.wishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

class ItemAdapter(
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val itemUrlTextView: TextView = itemView.findViewById(R.id.itemUrl)

        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            displayItemDetails(item)
            setItemClickListener(item)
            setItemLongClickListener(item)
        }

        private fun displayItemDetails(item: Item) {
            itemNameTextView.text = "Name: ${item.name}"
            itemPriceTextView.text = "Price: $${item.price}"
            itemUrlTextView.text = "URL: ${item.itemUrl}"
        }

        private fun setItemClickListener(item: Item) {
            itemUrlTextView.setOnClickListener {
                openItemUrl(item.itemUrl)
            }
        }

        private fun setItemLongClickListener(item: Item) {
            itemView.setOnLongClickListener {
                showDeleteConfirmationDialog(item)
                true
            }
        }

        private fun openItemUrl(itemUrl: String) {
            if (isValidUrl(itemUrl)) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl))
                itemView.context.startActivity(browserIntent)
            } else {
                showInvalidUrlDialog()
            }
        }

        private fun isValidUrl(url: String): Boolean {
            return url.startsWith("http://") || url.startsWith("https://")
        }

        private fun showInvalidUrlDialog() {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Invalid URL")
            builder.setMessage("The URL provided is not valid.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        private fun showDeleteConfirmationDialog(item: Item) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Delete Item")
            builder.setMessage("Are you sure you want to delete ${item.name}?")
            builder.setPositiveButton("Yes") { dialog, _ ->
                deleteItem(item)
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        private fun deleteItem(item: Item) {
            val position = items.indexOf(item)
            if (position != -1) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
