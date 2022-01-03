package com.example.retrofit_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_api.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException
import java.util.*

const val tag = "MainActivty"
class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private  lateinit var PopulationAdapter: PopulationAdapter

    private lateinit var masterPopulationList:ArrayList<Population>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible=true
            val response = try{
                retrofitInstance.api.getResponse()

            }catch (e:IOException){
                Log.e(tag,"No Internet Connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }catch (e:HttpException){
                Log.e(tag, "http error")
                binding.progressBar.isVisible = false
                return@launchWhenCreated

            }
            if(response.isSuccessful && response.body() !=null){
                masterPopulationList = response.body()?.data!! as ArrayList<Population>
                println(PopulationAdapter.populations.getOrNull(0))
                PopulationAdapter.populations = masterPopulationList.toList()
                PopulationAdapter.notifyDataSetChanged()

            }
            else{
                Log.e(tag,"Response unsuccessful")
//                println(retrofitInstance.api.getResponse())
            }
            binding.progressBar.isVisible = false
        }




    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView =  item?.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0?.isNotEmpty()==true) {
                    binding.recyclerView.visibility=View.VISIBLE
                    binding.noResult.visibility= View.GONE
                        PopulationAdapter.populations=masterPopulationList.filter {
                            it.city.contains(p0,true)
                        }
                    PopulationAdapter.notifyDataSetChanged()



                    if(PopulationAdapter.populations.isEmpty())
                    {
                        println("2")
//                        binding.recyclerView.visibility=View.GONE
                        binding.noResult.visibility= View.VISIBLE
                        PopulationAdapter.notifyDataSetChanged()


                    }

                    PopulationAdapter.notifyDataSetChanged()
                }
                else{

                    PopulationAdapter.populations=masterPopulationList.toList()
                    PopulationAdapter.notifyDataSetChanged()

                }
                return true
            }
        })



        return super.onCreateOptionsMenu(menu)
    }
    private  fun setUpRecyclerView()= binding.recyclerView.apply {
        PopulationAdapter= PopulationAdapter()
        adapter=PopulationAdapter
        layoutManager= LinearLayoutManager(this@MainActivity)
    }

}