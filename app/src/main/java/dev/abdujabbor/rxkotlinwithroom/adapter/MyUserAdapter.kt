package dev.abdujabbor.rxkotlinwithroom.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.abdujabbor.rxkotlinwithroom.database.AppDatabase
import dev.abdujabbor.rxkotlinwithroom.database.User
import dev.abdujabbor.rxkotlinwithroom.databinding.RvItemBinding

class MyUserAdapter(val appDatabase: AppDatabase) : ListAdapter<User, MyUserAdapter.Vh>(DiffUtils()) {

    class DiffUtils : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }
    }

    inner class Vh(val itemRvItemBinding: RvItemBinding) : ViewHolder(itemRvItemBinding.root) {
        fun onBind(user: User) {
            itemRvItemBinding.apply {
                tvId.text = user.id.toString()
                tvName.text = user.name
                tvNumber.text = user.number

                itemCard.setOnLongClickListener {
                    appDatabase.myUserDao().deleteUser(user)

                    true
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

}