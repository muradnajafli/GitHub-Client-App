package com.example.githubclient

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.RepositoryItemBinding


class RepositoryAdapter(private val context: Context, private val repositoryList: MutableList<Repository>) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {


    inner class RepositoryViewHolder(val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepositoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RepositoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositoryList[position]
        val myHolder = holder.binding
        myHolder.repositoryName.text = repository.name
        myHolder.description.text = repository.formattedDescription
        myHolder.root.setOnClickListener {
            val action = MainFragmentDirections.mainToDetail(repository.name, repository.owner.login)
            it.findNavController().navigate(action)
        }

    }

    override fun getItemCount() = repositoryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        repositoryList.clear()
        notifyDataSetChanged()

    }


}