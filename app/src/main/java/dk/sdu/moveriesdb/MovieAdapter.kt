package dk.sdu.moveriesdb

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dk.sdu.moveriesdb.database.movie.Movie

class MovieAdapter(private val data: ArrayList<Movie>, val context: Context, private val onItemClicked: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    inner class ViewHolder(item : View): RecyclerView.ViewHolder(item){
        val title : TextView = item.findViewById(R.id.title)
        val image : ImageView = item.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_name_image, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Image
        val uri = "@drawable/" + data[position].imageReference
        val imageResource = context.resources.getIdentifier(uri, null, context.packageName)
        val res = context.resources.getDrawable(imageResource, null)
        holder.image.setImageDrawable(res)

        holder.title.text = data[position].title

        //Listener for OnClick
        holder.title.setOnClickListener{
            onItemClicked(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}