package com.example.okcredit.Views.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.SupplierTransaction
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.ViewModel.SupplierViewModel
import com.example.okcredit.ViewModel.SupplierViewModelFactory
import com.example.okcredit.Views.values.*
import com.san.app.activity.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_received.*
import kotlinx.android.synthetic.main.activity_received.btnAddPhoto
import kotlinx.android.synthetic.main.activity_received.btnSubmit
import kotlinx.android.synthetic.main.activity_received.dateContainer
import kotlinx.android.synthetic.main.activity_received.etAmount
import kotlinx.android.synthetic.main.activity_received.etNote
import kotlinx.android.synthetic.main.activity_received.imageContainer
import kotlinx.android.synthetic.main.activity_received.image_view
import kotlinx.android.synthetic.main.activity_received.ivProfile
import kotlinx.android.synthetic.main.activity_received.removeImg
import kotlinx.android.synthetic.main.activity_received.toolbar
import kotlinx.android.synthetic.main.activity_received.transactLoader
import kotlinx.android.synthetic.main.activity_received.tvCurrencySymbol
import kotlinx.android.synthetic.main.activity_received.tvDate
import kotlinx.android.synthetic.main.activity_received.tvError
import kotlinx.android.synthetic.main.activity_received.tvHeaderAmount
import kotlinx.android.synthetic.main.activity_received.tvName
import kotlinx.android.synthetic.main.activity_received_suppplier.*
import okhttp3.ResponseBody
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ReceivedSuppplierActivity : BaseActivity(), View.OnClickListener {
    private var tag: String = "supplierActivity"

    private var supplier: Supplier? = null
    private var type: String? = null
    private var isDataValid: Boolean = false

    private var redColor: Int = 0
    private var greenColor: Int = 0

    private var imageURI: String = ""

    private var transact: SupplierTransaction? = null

    private var api: RestAPI? = null
    private var disposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_received_suppplier)

        //Initialize views
        initViews()

        setCustomerData()
        val appClass = application as OkCreditApplication

        val repository = appClass.repository
        val viewModelFactory = SupplierViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SupplierViewModel::class.java)
        val list = mutableListOf<Transaction>()
        val amount = etAmount1.text.toString()
        val time = tvDate1.text.toString()
        val note = etNote1.text.toString()
        val photo = btnAddPhoto1.textAlignment.toString()
        var customerEntity =
            SupplierTransaction(amount,type,time,note,photo,time)
        viewModel.addTransaction(customerEntity)

    }


    private fun initViews() {
        supplier = intent.getParcelableExtra("customer")
        type = intent.getStringExtra("type")
        Log.d(tag, "customer===> $supplier")

        toolbar1.setNavigationOnClickListener { onBackPressed() }

        disposable = CompositeDisposable()
        api = RestAdapter.getInstance()

        redColor = ContextCompat.getColor(this, R.color.red)
        greenColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        etAmount1.afterTextChanged { onAmountTextChange(it) }

        dateContainer1.setOnClickListener(this)
        btnSubmit1.setOnClickListener(this)
        btnAddPhoto1.setOnClickListener(this)
        removeImg1.setOnClickListener(this)
    }
    @SuppressLint("SimpleDateFormat")
    private fun setCustomerData() {
        if (supplier?.profileImage !== null) {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile1)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile1)
        }
        tvName1.text = supplier?.name

        if (supplier?.balanceType == getString(R.string.due)) {
            tvHeaderAmount1.setTextColor(redColor)
        } else {
            tvHeaderAmount1.setTextColor(greenColor)
        }

        tvHeaderAmount1.text = Tools.getCurrency2(supplier!!.balance)
        tvDate1.text = SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().time)

        if (type == "debit") {
            etAmount1.setTextColor(redColor)
            tvCurrencySymbol1.setTextColor(redColor)
        } else {
            etAmount1.setTextColor(greenColor)
            tvCurrencySymbol1.setTextColor(greenColor)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dateContainer1 -> {
                openCalender()
            }
            R.id.btnAddPhoto1 -> {
                requestStoragePermission()
            }
            R.id.removeImg1 -> {
                removeImage()
            }
            R.id.btnSubmit1 -> {
                addTransaction()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun openCalender() {
        val cal = Calendar.getInstance()

        val dpd = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                tvDate.text = SimpleDateFormat("dd MMM yyyy").format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }

    private fun addTransaction() {
        val amount = etAmount1.text.toString().trim()
        if (amount.isEmpty()) {
            isDataValid = false
            tvError1.visibility = View.VISIBLE
            tvError1.text = getString(R.string.valid_amount)
            return
        }
        val note = etNote1.text.toString().trim()

        transact = SupplierTransaction(
            amount,
            type,
            Timestamp(System.currentTimeMillis()).toString(),
            note,
            image = imageURI
        )

        supplier?.transactions?.add(transact!!)
        val intent = Intent().apply {
            putExtra("addTransaction", supplier)
        }
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
        // createTransact()
    }

    private fun createTransact() {
        val mobile = Prefs.getString("phone")
        val time = System.currentTimeMillis()
        disposable!!.add(
            api!!.createTransact(
                "1",
                mobile!!,
                transact?.amount!!,
                time.toString(),
                transact?.note!!
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoader() }
                .doFinally { hideLoader() }
                .subscribe({ handleSuccess(it) }) { handleError(it) }
        )
    }

    @SuppressLint("RestrictedApi")
    private fun showLoader() {
        btnSubmit1.visibility = View.GONE
        transactLoader1.visibility = View.VISIBLE
    }

    @SuppressLint("RestrictedApi")
    private fun hideLoader() {
        transactLoader1.visibility = View.GONE
        btnSubmit1.visibility = View.VISIBLE

    }

    private fun handleSuccess(data: ResponseBody) {
        Log.d(tag, "handleSuccess $data")
        supplier?.transactions?.add(transact!!)
        val intent = Intent().apply {
            putExtra("addTransaction", supplier)
        }
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()

    }


    private fun handleError(t: Throwable?) {
        Log.d(tag, "handleError:  ${t?.localizedMessage}")
        Toast.makeText(this, "Error : ${t?.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    private fun onAmountTextChange(s: String) {
        if (s.isNotEmpty()) {
            dateContainer1.visibility = View.VISIBLE
        } else {
            dateContainer1.visibility = View.GONE
        }
    }


    private fun removeImage() {
        image_view1.setImageDrawable(null)
        imageContainer1.visibility = View.GONE
        imageURI = ""
    }


    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(tag, "onActivityResult $requestCode $resultCode $data")
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) run {
                //for getting image from Camera
                imageContainer1.visibility = View.VISIBLE
                Glide.with(this)
                    .load(Uri.parse(mCurrentPhotoPath).path)
                    .into(image_view)

                imageURI = Uri.parse(mCurrentPhotoPath).path.toString()
            } else
                if (requestCode == SELECT_FILE) run {
                    //for getting image from file
                    val uri = data!!.data
                    val type = uri?.let { getMimeType(this, it) }
                    if (type != null && type.isNotEmpty() && type != "null") {
                        if (type.toLowerCase().contains("jpg")
                            || type.toLowerCase().contains("png")
                            || type.toLowerCase().contains("jpeg")
                        ) {
                            imageContainer1.visibility = View.VISIBLE
                            Glide.with(this)
                                .load(uri)
                                .into(image_view)

                            imageURI = uri.toString()

                        } else {
                            Toast.makeText(this, "Please provide valid image.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Please provide valid image.", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else
                    if (requestCode == 101) run {
                        //for runtime permission
                        requestStoragePermission()
                    }
        }
    }
}