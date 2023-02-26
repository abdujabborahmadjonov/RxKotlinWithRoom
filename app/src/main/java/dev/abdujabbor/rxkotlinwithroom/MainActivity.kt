package dev.abdujabbor.rxkotlinwithroom
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.abdujabbor.rxkotlinwithroom.adapter.MyUserAdapter
import dev.abdujabbor.rxkotlinwithroom.database.AppDatabase
import dev.abdujabbor.rxkotlinwithroom.database.User
import dev.abdujabbor.rxkotlinwithroom.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var appDatabase: AppDatabase
    lateinit var rvAdapter: MyUserAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appDatabase = AppDatabase.getInstance(this)
        rvAdapter = MyUserAdapter(appDatabase)
        binding.myRv.adapter = rvAdapter


        appDatabase.myUserDao().getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<List<User>>{
                override fun accept(t: List<User>?) {
                    rvAdapter.submitList(t)
                }
            })

        /** Adding new USER */
        binding.btnSave.setOnClickListener {
            val user = User()
            user.name=binding.edtName.text.toString()
            user.number=binding.edtNumber.text.toString()
            appDatabase.myUserDao().addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{it->
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                }

        }


    }
}