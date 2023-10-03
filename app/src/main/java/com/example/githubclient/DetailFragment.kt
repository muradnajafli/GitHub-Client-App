package com.example.githubclient


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.githubclient.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var gitHubService: GitHubService
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        gitHubService = Config.retrofit.create(GitHubService::class.java)

        val owner = args.owner
        val repoName = args.name
        callApi(owner, repoName)
        return binding.root
    }


    private fun callApi(owner: String, name: String) {
        gitHubService.listDetailRepos(owner, name).enqueue(object : Callback<DetailRepository> {
            override fun onResponse(
                call: Call<DetailRepository>,
                response: Response<DetailRepository>
            ) {
                if (response.isSuccessful) {
                    val detailRepository: DetailRepository? = response.body()
                    if (detailRepository != null) {
                        binding.repoName.visibility = View.VISIBLE
                        binding.issuesLabel.visibility = View.VISIBLE
                        binding.forksLabel.visibility = View.VISIBLE
                        binding.repoName.text = detailRepository.name
                        binding.issues.text = detailRepository.issuesCount.toString()
                        binding.forks.text = detailRepository.forksCount.toString()
                        if (detailRepository.parent == null) {
                            binding.parentRepoLabel.visibility = View.GONE
                        } else {
                            binding.parentRepoLabel.visibility = View.VISIBLE
                            binding.parentRepo.text = detailRepository.parent.fullName
                        }
                        binding.watchersLabel.visibility = View.VISIBLE
                        binding.watchers.text = detailRepository.watchersCount.toString()
                        binding.descriptionDetailLabel.visibility = View.VISIBLE
                        binding.descriptionDetail.text = detailRepository.description

                    }
                }
            }

            override fun onFailure(call: Call<DetailRepository>, t: Throwable) {
                Toast.makeText(requireContext(), "Cannot load repositories", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

