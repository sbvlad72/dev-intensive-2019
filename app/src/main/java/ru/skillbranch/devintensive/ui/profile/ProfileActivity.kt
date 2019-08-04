package ru.skillbranch.devintensive.ui.profile

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel
import java.util.*
import kotlin.collections.Map as Map1

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map1<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("M_ProfileActivity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initView(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer {updateUi(it)})
        viewModel.getTheme().observe(this, Observer {updateTheme(it)})
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity", "updateTheme")
        delegate.setLocalNightMode(mode)
    }

    private fun updateUi(profile: Profile) {
        profile.toMap().also {
            for ((k,v) in viewFields){
                v.text = it[k].toString()
            }
        }

    }

    private fun initView(savedInstanceState: Bundle?) {

        viewFields = mapOf(
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "nickName" to tv_nick_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener(View.OnClickListener {
            viewModel.switchTheme()
        })
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        for ((_,v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }
        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit){
            val filter: ColorFilter? = if (isEdit){
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            }else{
                null
            }

            val icon = if(isEdit){
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            }else{
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)

        }
    }

    private fun saveProfileInfo(){
        Profile(
             firstName = et_first_name.text.toString(),
             lastName =  et_last_name.text.toString(),
             about =  et_about.text.toString(),
             repository = et_repository.text.toString()
             //rating = tv_rating.text as Int,
             //respect = tv_respect.text as Int
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)

        Log.d("M_MainActivity", "onSaveInstanceState isEditMode ${isEditMode}")
    }

 //   override fun onClick(v: View?) {
 //       Log.d("M_MainActivity", "onClick")
 //   }

}
