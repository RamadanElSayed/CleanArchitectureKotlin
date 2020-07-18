package com.kotlin.cleanarchitecturekotlin.presentation.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.model.personimages.Profile
import com.kotlin.cleanarchitecturekotlin.R
import com.kotlin.cleanarchitecturekotlin.presentation.Constants

class PersonImagesAdapter (context: Context, var profileList: List<Profile>) :
    RecyclerView.Adapter<PersonImagesAdapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_person_images ,parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(profileList[position])

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return profileList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        fun bindItems(profile: Profile) {
            val img = itemView.findViewById(R.id.img_person) as ImageView

            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL+profile.filePath)
                .into(img)


        }
    }
}