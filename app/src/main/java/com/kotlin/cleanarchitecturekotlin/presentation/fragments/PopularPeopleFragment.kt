package com.kotlin.cleanarchitecturekotlin.presentation.fragments
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.interfaceview.PopularPeopleDetailsView
import com.example.task.model.popularpeople.result
import com.kotlin.cleanarchitecturekotlin.R
import com.kotlin.cleanarchitecturekotlin.data.model.paging.EndlessScrollListener
import com.kotlin.cleanarchitecturekotlin.presentation.adapter.PopularPeopleAdapter
import com.kotlin.cleanarchitecturekotlin.presentation.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.fragment_popular_people.view.*
class PopularPeopleFragment : Fragment(), PopularPeopleDetailsView {
    private lateinit var popularPeopleAdapter: PopularPeopleAdapter
    private lateinit var popularPeopleViewModel: PopularPeopleViewModel
    private var my_page = 1
    private lateinit var result_List: MutableList<result>
    private var totalPages: Int = 0
    private lateinit var endlessScrollListener: EndlessScrollListener
    private var Status: Boolean = false
    private lateinit var recyclerPopularPeople: RecyclerView
    private lateinit var progressBar_people: ProgressBar
    private lateinit var etSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_popular_people, container, false)

        result_List = mutableListOf<result>()
        etSearch = view.etSearch
        progressBar_people = view.progressBar_people

//        result_search_List=  mutableListOf<result>()
        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]
        // searchPeopleAdapter = PopularPeopleAdapter(applicationContext,result_search_List)
        popularPeopleAdapter = context?.let { PopularPeopleAdapter(it, result_List) }!!
        recyclerPopularPeople = view.recyclerPopularPeople
        recyclerPopularPeople.adapter = popularPeopleAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performPopularPeopleList()
        EditSearchChanger()
    }

    private fun performPopularPeopleList() {
        popularPeopleAdapter.onClick(this)
        initScroll()
        popularPeopleList(my_page);

    }

    fun initScroll() {
        recyclerPopularPeople.layoutManager = LinearLayoutManager(context)
        recyclerPopularPeople.setItemAnimator(DefaultItemAnimator())

        endlessScrollListener =
            object : EndlessScrollListener(recyclerPopularPeople.layoutManager!!) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    if (Status == false) {
                        if (currentPage < totalPages) {
                            my_page++
                            popularPeopleList(my_page)
                        }
                    } else {
                        if (currentPage < totalPages) {
                            my_page++
                            searchPeopleResult(my_page)
                        }
                    }
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            }
    }

    private fun popularPeopleList(page: Int) {
        //  if (isConnected) {
        progressBar_people.visibility = View.VISIBLE
        context?.let {
            popularPeopleViewModel.getPopularPeopleList(
                it, "889821def8006c20b36edf63a80b98fd",
                "en-US", 1
            ).observe(viewLifecycleOwner,
                Observer { popularPeopleModel ->
                    progressBar_people.visibility = View.GONE

                    if (popularPeopleModel.results != null) {
                        this.totalPages = popularPeopleModel.totalPages
                        //result_List.clear();
                        result_List.addAll(popularPeopleModel.results)

                        popularPeopleAdapter.notifyItemRangeInserted(
                            popularPeopleAdapter.getItemCount(),
                            result_List.size
                        )
                        //  Toast.makeText(this,"pop"+result_List.size,Toast.LENGTH_LONG).show()
                        recyclerPopularPeople.addOnScrollListener(endlessScrollListener)

                    }
                })
        }

    }

    private fun searchPeopleResult(page: Int) {
        context?.let {
            popularPeopleViewModel.searchPeopleResult(
                it, "889821def8006c20b36edf63a80b98fd",
                "en-US", etSearch.text.toString(), page, true, ""
            ).observe(
                viewLifecycleOwner, Observer { popularPeopleModel ->
                    // progressBar_people.visibility=View.GONE
                    this.totalPages = popularPeopleModel.totalPages
                    // Toast.makeText(this,result_List.size.toString(),Toast.LENGTH_LONG).show()
                    // result_List.clear()
                    result_List.addAll(popularPeopleModel.results)
                    popularPeopleAdapter.notifyItemRangeInserted(
                        popularPeopleAdapter.getItemCount(),
                        result_List.size
                    )
                    //   Toast.makeText(this,"search "+ result_List.size,Toast.LENGTH_LONG).show()
                    recyclerPopularPeople.addOnScrollListener(endlessScrollListener)

                }
            )
        }
    }

    private fun EditSearchChanger() {

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // result_List.clear()
                my_page = 1
                totalPages = 0
                if (s.isEmpty()) {
                    Status = false
                    popularPeopleList(my_page)
                } else {
                    Status = true
                    popularPeopleViewModel.onOmnibarInputStateChanged(etSearch.text.toString())
                    searchPeopleResult(my_page)

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {


            }
        })

    }

    override fun showPopularPeopleDetails(mresult: result) {

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_details)
        if (navController.currentDestination!!.id ==
            R.id.popularPeople
        ) {
            val fragmentDirections =
                PopularPeopleFragmentDirections.actionPopularPeopleToDetailsPeopleFragment()
            fragmentDirections.personId = mresult.id.toString()
            fragmentDirections.name = mresult.name
            fragmentDirections.popularity = mresult.popularity.toString()
            fragmentDirections.img = mresult.profilePath
            fragmentDirections.department = mresult.knownForDepartment
            navController.navigate(fragmentDirections)

        }
/*
"integer" -> IntType
"integer[]" -> IntArrayType
"long" -> LongType
"long[]" -> LongArrayType
"float" -> FloatType
"float[]" -> FloatArrayType
"boolean" -> BoolType
"boolean[]" -> BoolArrayType
"reference" -> ReferenceType
"reference[]" -> ReferenceArrayType
"string" -> StringType
"string[]" -> StringArrayType
null -> StringType
 */
    }
}