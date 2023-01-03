package com.olvera.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.databinding.ActivityDogListBinding
import com.olvera.dogedex.dogdetail.DogDetailComposeActivity

private const val GRID_SPAN_COUNT = 3

@ExperimentalCoilApi
class DogListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogListBinding

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWhell = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            // Pasar el dog a DogDetailActivity
            val intent = Intent(this, DogDetailComposeActivity::class.java)
            intent.putExtra(DogDetailComposeActivity.DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = adapter

       /* dogListViewModel.dogList.observe(this) {
            dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) {
            status ->

            when(status) {
                is ApiResponseStatus.Error -> {
                    loadingWhell.visibility = View.VISIBLE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Loading -> loadingWhell.visibility = View.VISIBLE

                is ApiResponseStatus.Success -> loadingWhell.visibility = View.GONE
            }
        }*/


    }

}