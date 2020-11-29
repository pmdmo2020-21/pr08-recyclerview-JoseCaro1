package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityItemBinding
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.synthetic.main.users_activity_item.view.*


class UsersActivityAdapter : RecyclerView.Adapter<UsersActivityAdapter.ViewHolder>() {

    private var data: List<User> = emptyList()

    init {
        setHasStableIds(true)
    }

    var onEditUserClickListener: ((Int) -> Unit)? = null
    var onDeleteUserClickListener: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UsersActivityItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


    override fun getItemCount(): Int = data.size

    fun getItem(position: Int) = data[position]

    fun submitList(newList: List<User>) {
        data = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: UsersActivityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            user.run {
                binding.cardName.text = nombre
                binding.cardPhone.text = phoneNumber
                binding.cardEmail.text = email
                binding.imgView.loadUrl(photoUrl)
            }
        }

        init {

            itemView.btnEdit.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onEditUserClickListener?.invoke(position)
                }
            }

            itemView.btnDel.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteUserClickListener?.invoke(position)
                }
            }
        }


    }


}