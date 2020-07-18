package com.kotlin.cleanarchitecturekotlin.presentation.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.interfaceview.PopularPeopleDetailsView
import com.example.task.model.popularpeople.result
import com.kotlin.cleanarchitecturekotlin.R
import com.kotlin.cleanarchitecturekotlin.presentation.Constants

class PopularPeopleAdapter(context: Context,var resultList: List<result>) :
        RecyclerView.Adapter<PopularPeopleAdapter.ViewHolder>() {
     lateinit var popularPeopleDetailsView :PopularPeopleDetailsView
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_popular_people, parent, false)
        return ViewHolder(v)
    }

    public fun onClick(popularPeopleDetailsView :PopularPeopleDetailsView)
    {
        this.popularPeopleDetailsView=popularPeopleDetailsView
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(resultList[position])
        holder.itemView.setOnClickListener {
            popularPeopleDetailsView.showPopularPeopleDetails(resultList[position])
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return resultList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        fun bindItems(mResult: result) {
            val img = itemView.findViewById(R.id.img) as ImageView
            val tName  = itemView.findViewById(R.id.tName) as TextView
            val tPopularity  = itemView.findViewById(R.id.tPopularity) as TextView

            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL+mResult.profilePath)
                .into(img)

            tName.text = mResult.name
            tPopularity.text = mResult.popularity.toString()

        }
    }
}