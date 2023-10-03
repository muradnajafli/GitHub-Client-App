package com.example.githubclient


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var gitHubService: GitHubService
    private lateinit var adapter: RepositoryAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orgName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        gitHubService = Config.retrofit.create(GitHubService::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = RepositoryAdapter(requireContext(), mutableListOf())

        binding.searchButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            orgName = binding.textInputLayout.editText?.text.toString()
            if (orgName.isNotEmpty()) {
                callRequestApi()
            } else {
                Toast.makeText(requireContext(), "Empty Input", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callRequestApi() {

        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                adapter.clearData()
            }, 3000)
            return

        }
        gitHubService.listRepos(this.orgName).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    val repoList: List<Repository>? = response.body()
                    if (repoList != null) {
                        val mutableRepoList = repoList.toMutableList() // Convert to MutableList
                        adapter = RepositoryAdapter(requireContext(), mutableRepoList)
                        binding.repoRv.layoutManager = linearLayoutManager
                        binding.repoRv.adapter = adapter
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No repos found for organization $orgName",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Cannot load repositories", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

