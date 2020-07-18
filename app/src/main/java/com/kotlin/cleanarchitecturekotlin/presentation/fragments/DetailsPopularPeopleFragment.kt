package com.kotlin.cleanarchitecturekotlin.presentation.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.task.model.personimages.PersonImagesModel
import com.kotlin.cleanarchitecturekotlin.R
import com.kotlin.cleanarchitecturekotlin.presentation.Constants
import com.kotlin.cleanarchitecturekotlin.presentation.adapter.PersonImagesAdapter
import com.kotlin.cleanarchitecturekotlin.presentation.viewmodel.DetailsPopularViewModel
import kotlinx.android.synthetic.main.fragment_details_popular_people.view.*

class DetailsPopularPeopleFragment : Fragment() {
    lateinit var personId: String
    lateinit var name: String
    lateinit var popularity: String
    lateinit var image: String
    lateinit var department: String
    lateinit var personImagesAdapter: PersonImagesAdapter
    lateinit var personImagesViewModel: DetailsPopularViewModel
    lateinit var viewLayout:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewLayout = inflater.inflate(R.layout.fragment_details_popular_people, container, false)
        personImagesViewModel = ViewModelProvider(this)[DetailsPopularViewModel::class.java]
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        personImagesList()
    }

    private fun personImagesList() {
        context?.let {
            personImagesViewModel.getPersonImagesList(
                it, personId
                ,"889821def8006c20b36edf63a80b98fd").observe(viewLifecycleOwner,
                Observer { personImagesModel ->
                    if (personImagesModel != null) {
                        viewLayout.recyclerPersonImages.apply {
                            layoutManager= GridLayoutManager(context, 2)
                            personImagesAdapter = PersonImagesAdapter(context,personImagesModel.profiles)
                            adapter=personImagesAdapter

                        }

                    }

                })
        }
    }
    private fun getData() {
        if (arguments != null) {
            personId = arguments?.getString("personId")!!
            name = arguments?.getString("name")!!
            popularity = arguments?.getString("popularity")!!
            image = arguments?.getString("img")!!
            department = arguments?.getString("department")!!
            viewLayout.tName.text=name
            viewLayout.tPopularity.text="Popularity: "+popularity
            viewLayout.tDepartment.text="Known for department : "+department

            Glide.with(this).load(Constants.IMAGE_BASE_URL+image).into(viewLayout.img);
        }

    }
}