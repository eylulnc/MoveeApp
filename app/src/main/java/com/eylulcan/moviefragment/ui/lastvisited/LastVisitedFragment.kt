package com.eylulcan.moviefragment.ui.lastvisited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import coil.compose.rememberImagePainter
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.util.Utils
import com.eylulcan.moviefragment.ui.theme.BlackGray
import com.eylulcan.moviefragment.ui.theme.MovieFragmentTheme
import com.eylulcan.moviefragment.ui.theme.WhileLightGrey
import com.eylulcan.moviefragment.ui.theme.latoFontFamily
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val MOVIE_KEY = "movieId"

@AndroidEntryPoint
class LastVisitedFragment @Inject constructor() :
    Fragment() {

    private val lastVisitedViewModel: LastVisitedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val view = ComposeView(requireContext())
        lastVisitedViewModel.readFromDB()
        view.apply {
           setContent {
               //val navController = rememberNavController()
               Surface(
                    color = BlackGray,
                    modifier = Modifier.fillMaxSize()
                ) {
                    SetupUI(viewModel = lastVisitedViewModel, this@LastVisitedFragment)
                }
            }
            return view
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupUI(
    viewModel: LastVisitedViewModel,
    context: LastVisitedFragment
) {
    val movieList by remember { viewModel.movieList }
    val isLoading by remember { viewModel.isLoading }


    GridView(movieList, context)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun GridView(movieList: ArrayList<MovieDaoEntity>, context: LastVisitedFragment) {
    val movies = movieList.toList()
    MovieFragmentTheme(
        darkTheme = true
    ) {
        Scaffold(content = {
            LazyVerticalGrid(
                cells = GridCells.Fixed(3)
            ) {
                items(movies.size) {
                    Card(
                        backgroundColor = BlackGray, modifier =
                        Modifier
                            .padding(start = 8.dp, top = 0.dp, bottom = 16.dp)
                            .clickable {
                                val movieDataBundle = bundleOf(MOVIE_KEY to movies[it].id)
                               findNavController(context).navigate(
                                    R.id.action_lastVisitedFragment_to_movieDetailFragment,
                                    movieDataBundle, null, null
                                )
                            },
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp, bottom = 0.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                val url = Utils.BASE_IMAGE_URL_300 + movies[it].posterPath
                                Image(
                                    painter = rememberImagePainter(url),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(150.dp)
                                )
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(4.dp, 4.dp)
                                ) {
                                    Text(
                                        text = movies[it].title,
                                        style = MaterialTheme.typography.body1,
                                        color = WhileLightGrey,
                                        textAlign = TextAlign.Center,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.background(BlackGray),
                                        fontStyle = FontStyle.Normal,
                                        fontFamily = latoFontFamily
                                    )
                                }
                            }
                        })
                }
            }
        })
    }
}






