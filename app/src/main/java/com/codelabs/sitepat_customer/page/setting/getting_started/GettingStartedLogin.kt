package com.codelabs.sitepat_customer.page.setting.getting_started

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.helper.Utils
import com.codelabs.sitepat_customer.page.password.ForgotPasswordActivity
import com.codelabs.sitepat_customer.page.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import me.samlss.lighter.Lighter
import me.samlss.lighter.interfaces.OnLighterListener
import me.samlss.lighter.interfaces.OnLighterViewClickListener
import me.samlss.lighter.parameter.Direction
import me.samlss.lighter.parameter.LighterParameter
import me.samlss.lighter.parameter.MarginOffset
import me.samlss.lighter.shape.RectShape

class GettingStartedLogin : BaseActivity() {

    lateinit var lighter: Lighter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lighter = Lighter.with(this)

        if (intent.getBooleanExtra("IS_HIGHLIGHT_LOGIN", false)) {
            highlightLogin()
        } else if (intent.getBooleanExtra("IS_HIGHLIGHT_REGIST",false)){
            highlightRegist()
        }else{
            highlightForgotPassword()
        }


    }

    private fun highlightForgotPassword() {
        lighter.addHighlight(
            LighterParameter.Builder()
                .setHighlightedViewId(R.id.tv_forgot_password)
                .setLighterShape(RectShape(5f, 5f, 30f))
                .setTipViewRelativeOffset(MarginOffset(150, 0, 30, 0))
                .setTipView(
                    Utils.createCommonTipViewTop(
                        this,
                        "Pada halaman login, klik Forgot Password",
                        90
                    )
                )
                .setTipViewRelativeDirection(Direction.TOP)
                .build()
        ).setOnLighterListener(object : OnLighterViewClickListener, OnLighterListener {
            override fun onClick(view: View?) {
            }

            override fun onShow(index: Int) {
            }

            override fun onDismiss() {
                val intent = Intent(this@GettingStartedLogin,ForgotPasswordActivity::class.java)
                intent.putExtra("IS_HIGHLIGHT_FORGOT",true)
                startActivity(intent)
                finish()
            }

        })
        lighter.show()
    }

    private fun highlightRegist() {


        scroll_view.post {
            scroll_view.fullScroll(View.FOCUS_DOWN)

        }
        Handler().postDelayed({
            lighter.addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.tv_register)
                    .setLighterShape(RectShape(5f, 5f, 30f))
                    .setTipViewRelativeOffset(MarginOffset(150, 0, 30, 0))
                    .setTipView(
                        Utils.createCommonTipViewTop(
                            this,
                            "Pada halaman login, klik tombol Register",
                            90
                        )
                    )
                    .setTipViewRelativeDirection(Direction.TOP)
                    .build()
            ).setOnLighterListener(object : OnLighterViewClickListener, OnLighterListener {
                override fun onClick(view: View?) {
                }

                override fun onShow(index: Int) {
                }

                override fun onDismiss() {
                    val intent = Intent(this@GettingStartedLogin,RegisterActivity::class.java)
                    intent.putExtra("IS_HIGHLIGHT_REGISTER",true)
                    startActivity(intent)
                    finish()
                }

            })
            lighter.show()
        }, 500)


    }

    private fun highlightLogin() {
        lighter.addHighlight(
            LighterParameter.Builder()
                .setHighlightedViewId(R.id.container_input)
                .setTipView(
                    Utils.createCommonTipViewBottom(
                        this,
                        "Input email dan password"
                    )
                )
                .setLighterShape(RectShape(5f, 5f, 30f))
                .setTipViewRelativeDirection(Direction.BOTTOM)
                .setTipViewRelativeOffset(MarginOffset(150, 0, 30, 0))
                .build()
        )

        lighter.addHighlight(
            LighterParameter.Builder()
                .setHighlightedViewId(R.id.btn_login)
                .setTipView(
                    Utils.createCommonTipViewBottom(
                        this,
                        "Klik Login"
                    )
                )
                .setLighterShape(RectShape(5f, 5f, 30f))
                .setTipViewRelativeDirection(Direction.BOTTOM)
                .setTipViewRelativeOffset(MarginOffset(150, 0, 30, 0))
                .build()
        )

        lighter.addHighlight(
            LighterParameter.Builder()
                .setHighlightedViewId(R.id.container_login_medsos)
                .setTipView(
                    Utils.createCommonTipViewTop(
                        this,
                        "Atau login dengan sosial media"
                    )
                )
                .setLighterShape(RectShape(5f, 5f, 30f))
                .setTipViewRelativeDirection(Direction.TOP)
                .setTipViewRelativeOffset(MarginOffset(150, 0, 30, 0))
                .build()
        ).setOnLighterListener(object : OnLighterViewClickListener, OnLighterListener {
            override fun onClick(view: View?) {
            }

            override fun onShow(index: Int) {
            }

            override fun onDismiss() {
                finish()
            }

        })


        lighter.show()
    }

}