package com.codelabs.dokter_mobil_customer.page.account

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.dialog.SelectImageDialog
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.helper.Utils
import com.codelabs.dokter_mobil_customer.imagepicker.FilePickUtils
import com.codelabs.dokter_mobil_customer.imagepicker.LifeCycleCallBackManager
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost
import com.codelabs.dokter_mobil_customer.viewmodel.ItemMyCar
import com.codelabs.dokter_mobil_customer.viewmodel.eventbus.PickImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_new_car.*
import kotlinx.android.synthetic.main.toolbar_back.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class AddNewCarActivity : BaseActivity(), FilePickUtils.OnFileChoose {
    private lateinit var data: ItemMyCar
    private var isSelectedImage = false
    lateinit var lifeCycleCallBackManager: LifeCycleCallBackManager
    lateinit var filePickUtils: FilePickUtils
    private val CAMERA_PERMISSION = 11
    private val STORAGE_PERMISSION_IMAGE = 22
    lateinit var foto: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_car)
        initView()
    }

    private fun initView() {

        filePickUtils = FilePickUtils(this, this)
        lifeCycleCallBackManager = filePickUtils.callBackManager
        btn_add_image.setOnClickListener {
            openSelectImage()

        }

        iv_new_car.setOnClickListener {
            openSelectImage()
        }

        btn_add_car.setOnClickListener {
            addCar()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        tv_title.text = getString(R.string.new_car)
        iv_back.setOnClickListener { finish() }

        if (intent.getBooleanExtra("IS_EDIT", false)) {
            tv_title.text = getString(R.string.edit_car)
            data = intent.getSerializableExtra("DATA") as ItemMyCar

            if (data.image.isNotEmpty()) {
                btn_add_image.visibility = View.GONE
                Picasso.get().load(data.image).into(iv_new_car)
            }
            txt_car_name.setText(data.carName)
            txt_no_plate.setText(data.carPlateNumber)
            txt_year.setText(data.carYear)
            txt_color.setText(data.carColor)
            txt_cc.setText(data.carCc)
            btn_add_car.text = getString(R.string.edit_car)
            btn_add_car.setOnClickListener {
                edit()
            }
        }

    }

    private fun edit() {
        if (!valid()) {
            return
        }
        val params = HashMap<String, RequestBody>()
        params["carName"] = Utils.createRequestBody(txt_car_name.text.toString().trim())
        params["carCc"] = Utils.createRequestBody(txt_cc.text.toString().trim())
        params["carColor"] = Utils.createRequestBody(txt_color.text.toString().trim())
        params["carYear"] = Utils.createRequestBody(txt_year.text.toString().trim())
        params["carPlateNumber"] = Utils.createRequestBody(txt_no_plate.text.toString().trim())
        showDialogProgress("Saving car")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token

        if (isSelectedImage) {
            val imageParams = Utils.createRequestImage(foto, "image")

            val call: Call<DoPost> =
                ApiUtils.getApiService().editCar(auth, data.carId.toString(), params, imageParams);
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
                        showToast(getString(R.string.toast_onfailure))
                    }
                }
            })
        } else {
            val call: Call<DoPost> =
                ApiUtils.getApiService().editCar(auth, data.carId.toString(), params);
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
                        showToast(getString(R.string.toast_onfailure))
                    }
                }
            })
        }
    }

    private fun addCar() {
        if (!valid()) {
            return
        }
        val params = HashMap<String, RequestBody>()
        params["carName"] = Utils.createRequestBody(txt_car_name.text.toString().trim())
        params["carCc"] = Utils.createRequestBody(txt_cc.text.toString().trim())
        params["carColor"] = Utils.createRequestBody(txt_color.text.toString().trim())
        params["carYear"] = Utils.createRequestBody(txt_year.text.toString().trim())
        params["carPlateNumber"] = Utils.createRequestBody(txt_no_plate.text.toString().trim())

        val imageParams = Utils.createRequestImage(foto, "image")

        showDialogProgress("Saving car")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<DoPost> = ApiUtils.getApiService().addCar(auth, params, imageParams);
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
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })

    }

    private fun valid(): Boolean {

        if (!intent.getBooleanExtra("IS_EDIT", false))
            if (!isSelectedImage) {
                showToast("Select image")
                return false
            }
        if (txt_car_name.text!!.isEmpty()) {
            showToast("Car name is empty")
            return false
        }
        if (txt_no_plate.text!!.isEmpty()) {
            showToast("Police number is empty")
            return false
        }
        if (txt_year.text!!.isEmpty()) {
            showToast("Year is empty")
            return false
        }
        if (txt_color.text!!.isEmpty()) {
            showToast("Color is empty")
            return false
        }
        if (txt_cc.text!!.isEmpty()) {
            showToast("Cc is empty")
            return false
        }
        return true
    }

    private fun openSelectImage() {
        val dialog = SelectImageDialog.newInstance()
        dialog.show(supportFragmentManager, "")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            if (lifeCycleCallBackManager != null) {
                lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data)
            }
        }
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
        btn_add_image.visibility = View.GONE
        var imageFoto = File(fileUri)
        foto = imageFoto
        isSelectedImage = true
        val bitmap = BitmapFactory.decodeFile(imageFoto.getPath())
        val byteArrayOutputStream =
            ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream)
        Picasso.get().load(imageFoto).into(iv_new_car)
    }

}