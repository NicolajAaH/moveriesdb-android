package dk.sdu.moveriesdb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.sdu.moveriesdb.database.movie.Movie
import dk.sdu.moveriesdb.database.movie.MovieDatabase


class ListFragment : Fragment(), FragmentManager.OnBackStackChangedListener {
    //Initialize
    lateinit var db: MovieDatabase
    lateinit var fetchThread: FetchThread
    lateinit var initThread: InitThread
    lateinit var adapter: MovieAdapter

    var movieList: ArrayList<Movie> = arrayListOf()

    inner class InitThread : Thread() { //Populate DB
        override fun run() {
            super.run()
            var movie1 = Movie(1, "Django Unchained", 2012, "Quentin Tarantino", "djangounchained")
            var movie2 = Movie(2, "Shrek", 2001, "Andrew Adamson", "shrek")
            var movie3 = Movie(3, "Pulp Fiction", 1994, "Quentin Tarantino", "pulpfiction")
            var movie4 = Movie(4, "Get Out", 2017, "Jordan Peele", "getout")
            var movie5 = Movie(5, "Saving Private Ryan", 1998, "Steven Spielberg", "ryan")
            var movie6 = Movie(6, "Gladiator", 2000, "Ridley Scott", "gladiator")

            db.movieDao().insert(movie1)
            db.movieDao().insert(movie2)
            db.movieDao().insert(movie3)
            db.movieDao().insert(movie4)
            db.movieDao().insert(movie5)
            db.movieDao().insert(movie6)
            val listMovie = db.movieDao().getAll()

            movieList.addAll(listMovie)
        }
    }

    inner class FetchThread : Thread() { //Receive data from DB
        override fun run() {
            super.run()
            var listMovie = db.movieDao().getAll()
            if (listMovie.isEmpty()) { //If empty, start initialization of data
                initThread = InitThread()
                initThread.start()
            } else {
                movieList.addAll(listMovie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = MovieDatabase.getAppDatabase(requireContext())!!
        fetchThread = FetchThread()
        fetchThread.start() //Start thread for loading in data
        parentFragmentManager.addOnBackStackChangedListener(this) //Ensure this is part of the navigation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list, container, false)

        if(movieList.isEmpty()){
            Thread.sleep(16) //If its empty, ensure it has enough time to load from DB, max 16ms
        }

        adapter = MovieAdapter(movieList, requireContext()) { movie ->
            run {
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragmentContainerView,
                    MovieDetailsFragment.newInstance(
                        movie.title,
                        movie.director,
                        movie.releaseYear,
                        movie.imageReference
                    )
                ).addToBackStack("details").commit()
            }
        }

        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        return view
    }

    override fun onBackStackChanged() {
    }

}