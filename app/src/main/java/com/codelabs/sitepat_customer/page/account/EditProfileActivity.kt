package com.codelabs.sitepat_customer.page.account


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.dialog.SelectImageDialog
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.helper.Utils
import com.codelabs.sitepat_customer.imagepicker.FilePickUtils
import com.codelabs.sitepat_customer.imagepicker.LifeCycleCallBackManager
import com.codelabs.sitepat_customer.viewmodel.DoPost
import com.codelabs.sitepat_customer.viewmodel.Profile
import com.codelabs.sitepat_customer.viewmodel.eventbus.PickImage
import com.codelabs.sitepat_customer.viewmodel.param.UpdateAddress
import com.codelabs.sitepat_customer.viewmodel.param.UpdateProfil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.toolbar_back.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import android.view.WindowManager

import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.R
import android.util.Log
import android.widget.Toast

import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener


class EditProfileActivity : BaseActivity(), FilePickUtils.OnFileChoose {
    private val RESULT_ALAMAT: Int = 100
    private lateinit var dataProfile: Profile.DataProfile
    private lateinit var adapter: AddressesAdapter
    private var isSelectedImage = false
    lateinit var lifeCycleCallBackManager: LifeCycleCallBackManager
    lateinit var filePickUtils: FilePickUtils
    private val CAMERA_PERMISSION = 11
    private val STORAGE_PERMISSION_IMAGE = 22
    lateinit var foto: File
    private val TAG = "EditProfile"
    var selection = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codelabs.sitepat_customer.R.layout.activity_edit_profile)

        initView()
//        showGender()
    }

    private fun initView() {
        filePickUtils = FilePickUtils(this, this)
        lifeCycleCallBackManager = filePickUtils.callBackManager

        iv_profil.setOnClickListener {
            openSelectImage()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(com.codelabs.sitepat_customer.R.string.edit_profile)

        rv_addresses.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = AddressesAdapter(this, listOf())
        rv_addresses.adapter = adapter

//        val bottomSheetGender = GenderBottomSheet()
        txt_gender.setOnClickListener {
////            bottomSheetGender.show(supportFragmentManager, "BottomSheetGender")
            showGender()
        }

        getProfile()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    fun getProfile() {
        showDialogProgress("Getting My Account")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<Profile> = ApiUtils.getApiService().getProfile(auth)
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, data: Response<Profile>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        dataProfile = response?.dataProfile!!
                        setData()
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(com.codelabs.sitepat_customer.R.string.toast_onfailure))
                }
            }
        })

    }

    private fun setData() {
        if (dataProfile.image!!.length > 0)
            Picasso.get().load(dataProfile.image).into(iv_profil)

        txt_name.setText(dataProfile.customerName)
        txt_email.setText(dataProfile.customerEmail)
        txt_phone.setText(dataProfile.customerPhone)
        txt_gender.setText(dataProfile.customerGender)

        val add = Profile.Addresses()
        add.input = true
        dataProfile.addresses?.add(add)
        adapter.items = dataProfile.addresses
        adapter.notifyDataSetChanged()

        btn_save.setOnClickListener {
            saveProfile()
        }

    }

    @Subscribe
    fun onClickItem(data: Profile.Addresses) {
        val intent = Intent(this, TambahAlamatActivity::class.java)
        intent.putExtra("DATA", data)
        startActivityForResult(intent, RESULT_ALAMAT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_ALAMAT && resultCode == RESULT_OK) {
            val data = data?.getSerializableExtra("DATA") as Profile.Addresses
            when (data.action) {
                "ADD" -> {
                    dataProfile.addresses.set(data.position, data)

                    val add = Profile.Addresses()
                    add.input = true
                    dataProfile.addresses?.add(add)
                }
                "EDIT" -> {
                    dataProfile.addresses.set(data.position, data)
                }
                "DELETE" -> {
                    dataProfile.addresses.removeAt(data.position)
                }
            }
            adapter.notifyDataSetChanged()
        } else {
            lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun valid(): Boolean {

        if (txt_name.text!!.isEmpty()) {
            showToast("Input name")
            return false
        }
        if (txt_email.text!!.isEmpty()) {
            showToast("Input email")
            return false
        }
        if (txt_phone.text!!.isEmpty()) {
            showToast("Input phone")
            return false
        }
        if (txt_gender.text!!.isEmpty()) {
            showToast("Input gender")
            return false
        }
        return true
    }

    private fun saveProfile() {
        if (!valid()) {
            return
        }

        val valueAddress = ArrayList<UpdateAddress>()
        dataProfile.addresses.forEach {
            if (!it.isInput)
                valueAddress.add(UpdateAddress(name = it.name, content = it.address))
        }
        if (valueAddress.size == 0) {
            showToast("Input address")
            return
        }

        val param = UpdateProfil(
            customerName = txt_name.text.toString(),
            customerEmail = txt_email.text.toString(),
            customerPhone = txt_phone.text.toString(),
            customerGender = txt_gender.text.toString(),
            address = valueAddress
        )

        showDialogProgress("Saving Profile")

        if (!isSelectedImage) {
            val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
            val call: Call<DoPost> = ApiUtils.getApiService().updateProfil(auth, param)
            call.enqueue(object : Callback<DoPost> {
                override fun onResponse(call: Call<DoPost>, data: Response<DoPost>) {
                    hideDialogProgress()
                    if (data.isSuccessful) {
                        val response = data.body()
                        if (data.code() == 200) {
                            showToast(response?.message)
                            finish()
                        }
                    } else {
                        val error = ErrorUtils.parseError(data)
                        showToast(error.message())
                    }
                }

                override fun onFailure(call: Call<DoPost>, t: Throwable) {
                    if (!call.isCanceled) {
                        hideDialogProgress()
                        showToast(getString(com.codelabs.sitepat_customer.R.string.toast_onfailure))
                    }
                }
            })
        } else {
            val params = HashMap<String, RequestBody>()
            params["customerName"] = Utils.createRequestBody(param.customerName.trim())
            params["customerPhone"] = Utils.createRequestBody(param.customerPhone.trim())
            params["customerEmail"] = Utils.createRequestBody(param.customerEmail.trim())
            params["customerGender"] = Utils.createRequestBody(param.customerGender.trim())
//            params["address"] = Utils.createRequestJson(Gson().toJson(param.address))

            for ((index, valueAddress) in valueAddress.withIndex()) {
                params["address[${index}][name]"] = Utils.createRequestBody(valueAddress.name)
                params["address[${index}][content]"] = Utils.createRequestBody(valueAddress.content)
            }

            val imageParams = Utils.createRequestImage(foto, "image")
            val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
            val call: Call<DoPost> =
                ApiUtils.getApiService().updateProfile(auth, params, imageParams)
            call.enqueue(object : Callback<DoPost> {
                override fun onResponse(call: Call<DoPost>, data: Response<DoPost>) {
                    hideDialogProgress()
                    if (data.isSuccessful) {
                        val response = data.body()
                        if (data.code() == 200) {
                            showToast(response?.message)
                            finish()
                        }
                    } else {
                        val error = ErrorUtils.parseError(data)
                        showToast(error.message())
                    }
                }

                override fun onFailure(call: Call<DoPost>, t: Throwable) {
                    if (!call.isCanceled) {
                        hideDialogProgress()
                        showToast(getString(com.codelabs.sitepat_customer.R.string.toast_onfailure))
                    }
                }
            })
        }
    }

    @Subscribe
    fun onSelectImage(data: PickImage) {
        if (data.type == 1) {
            filePickUtils.requestImageCamera(
                CAMERA_PERMISSION,
                true,
                false,
                false
            )
        } else {
            filePickUtils.requestImageGallery(
                STORAGE_PERMISSION_IMAGE,
                true,
                false,
                false
            )

        }
    }

    private fun openSelectImage() {
        val dialog = SelectImageDialog.newInstance()
        dialog.show(supportFragmentManager, "")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        lifeCycleCallBackManager.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onFileChoose(fileUri: String?, requestCode: Int) {
        var imageFoto = File(fileUri)
        foto = imageFoto
        isSelectedImage = true
        val bitmap = BitmapFactory.decodeFile(imageFoto.path)
        val byteArrayOutputStream =
            ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream)
        Picasso.get().load(imageFoto).into(iv_profil)
    }

//    private fun showPopupWindow(): View.OnClickListener {
//        return object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val popUp: PopupWindow = popupWindowsort()
//                popUp.showAsDropDown(v, 0, 0) // show popup like dropdown list
//            }
//        }
//    }
//
//    /**
//     * show popup window method reuturn PopupWindow
//     */
//    private fun popupWindowsort(): PopupWindow {
//
//        // initialize a pop up window type
//        popupWindow = PopupWindow(this)
//        val sortList = ArrayList<String>()
//        sortList.add("A to Z")
//        sortList.add("Z to A")
//        sortList.add("Low to high price")
//        val adapter = ArrayAdapter(
//            this, android.R.layout.simple_dropdown_item_1line,
//            sortList
//        )
//        // the drop down list is a list view
//        val listViewSort = ListView(this)
//
//        // set our adapter and pass our pop up window contents
//        listViewSort.setAdapter(adapter)
//
//        // set on item selected
//        listViewSort.setOnItemClickListener(onItemClickListener())
//
//        // some other visual settings for popup window
//        popupWindow.setFocusable(true)
//        popupWindow.setWidth(250)
//        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
//        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT)
//
//        // set the listview as popup content
//        popupWindow.setContentView(listViewSort)
//        return popupWindow
//    }

    private fun showGender() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            resources.getStringArray(com.codelabs.sitepat_customer.R.array.Gender_Names)
        )
        txt_gender.setAdapter(arrayAdapter)
        txt_gender.isCursorVisible = false
        txt_gender.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            txt_gender.showDropDown()
            selection = parent.getItemAtPosition(position) as String
    //            Toast.makeText(
    //                applicationContext, selection,
    //                Toast.LENGTH_SHORT
    //            ).show()
        }
        txt_gender.setOnClickListener {
            txt_gender.showDropDown()
        }
    }

}