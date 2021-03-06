package com.tt.lvruheng.eyepetizer.search

import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.SearchAdapter
import com.tt.lvruheng.eyepetizer.ui.ResultActivity
import com.tt.lvruheng.eyepetizer.utils.KeyBoardUtils
import kotlinx.android.synthetic.main.search_fragment.*


/**
 * Created by lvruheng on 2017/7/9.
 */
const val SEARCH_TAG = "SearchFragment"

class SearchFragment : DialogFragment(),
        ViewTreeObserver.OnPreDrawListener, DialogInterface.OnKeyListener,
        View.OnClickListener, CircularRevealAnim.AnimListener {
    var data: MutableList<String> = arrayListOf("脱口秀", "城会玩", "666", "笑cry", "漫威",
            "清新", "匠心", "VR", "心理学", "舞蹈", "品牌广告", "粉丝自制", "电影相关", "萝莉", "魔性"
            , "第一视角", "教程", "毕业设计", "奥斯卡", "燃", "冰与火之歌", "温情", "线下campaign", "公益")
    lateinit var mRootView: View
    lateinit var mCircularRevealAnim: CircularRevealAnim
    lateinit var mAdatper: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)

    }

    override fun onStart() {
        super.onStart()

        initDialog()
    }

    private fun initDialog() {

        val window = dialog.window
        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.98).toInt()
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mRootView = inflater!!.inflate(R.layout.search_fragment, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setRecyclerViewData()
    }

    private fun initViews() {

        tv_hint.typeface = Typeface.createFromAsset(resources.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        iv_search_back.setOnClickListener(this)
        iv_search_search.setOnClickListener(this)

        dialog.setOnKeyListener(this)

        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim.animListener = this
    }

    private fun setRecyclerViewData() {

        mAdatper = SearchAdapter(activity, data as ArrayList<String>)
        mAdatper.setOnDialogDismissListener(object : SearchAdapter.onDialogDismiss {
            override fun onDismiss() {
                hideAnim()
            }
        })

        val manager = FlexboxLayoutManager()
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdatper
    }

    override fun onHideAnimEnd() {

        et_search_keyword.setText("")
        dismiss()
    }

    override fun onShowAnimEnd() {

        if (isVisible) {
            KeyBoardUtils.openKeyboard(activity, et_search_keyword);
        }
    }

    override fun onPreDraw(): Boolean {

        iv_search_search.viewTreeObserver.removeOnPreDrawListener(this)
        mCircularRevealAnim.showView(iv_search_search, mRootView)
        return true
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            hideAnim()
        } else if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            search()
        }
        return false
    }

    private fun search() {

        val searchKey = et_search_keyword.text.toString()
        if (TextUtils.isEmpty(searchKey.trim({ it <= ' ' }))) {
            Toast.makeText(activity, "请输入关键字", Toast.LENGTH_SHORT).show()
        } else {
            hideAnim()
            var keyWord = et_search_keyword.text.toString().trim()
            var intent = Intent(activity, ResultActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            activity?.startActivity(intent)
        }
    }

    private fun hideAnim() {

        KeyBoardUtils.closeKeyboard(activity, et_search_keyword)
        mCircularRevealAnim.hideView(iv_search_search, mRootView)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.iv_search_back -> {
                hideAnim()
            }

            R.id.iv_search_search -> {
                search()

            }

        }
    }

}