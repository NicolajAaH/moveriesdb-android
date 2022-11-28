package dk.sdu.moveriesdb

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class MovieDetailsFragment : Fragment(R.layout.detailed_fragment){

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.detailed_fragment, container, false)

        val title = view.findViewById<TextView>(R.id.titleDetailed)
        title.text = requireArguments().getString("title")

        val release = view.findViewById<TextView>(R.id.release)
        release.text = "Release year: " + requireArguments().getInt("releaseYear").toString()

        val director = view.findViewById<TextView>(R.id.director)
        director.text = "Director: " + requireArguments().getString("director")

        val image = view.findViewById<ImageView>(R.id.movieImage)
        val uri = "@drawable/" + requireArguments().getString("imageReference")

        val imageResource = resources.getIdentifier(uri, null, requireContext().packageName)

        val res = resources.getDrawable(imageResource, null)
        image.setImageDrawable(res)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MovieDetailsFragment.
         */
        @JvmStatic
        fun newInstance(title: String, director: String, releaseYear: Int, imageReference: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("director", director)
                    putString("imageReference", imageReference)
                    putInt("releaseYear", releaseYear)
                }
            }
    }


}