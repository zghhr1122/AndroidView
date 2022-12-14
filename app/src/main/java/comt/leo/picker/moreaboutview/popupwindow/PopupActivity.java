package comt.leo.picker.moreaboutview.popupwindow;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import comt.leo.picker.moreaboutview.R;
import comt.leo.picker.moreaboutview.activity.MainActivity;
import comt.leo.picker.moreaboutview.dialog.DialogUtil;
import comt.leo.picker.moreaboutview.myView.WrapListView;
import comt.leo.picker.moreaboutview.toast.MyToast;

/**
 * Created by leo
 * on 2019/4/24.
 */
public class PopupActivity extends AppCompatActivity implements View.OnClickListener, CommonPopupWindow.PopItemListener {
    private Button buttonPanel;
    private CommonPopupWindow popupWindow;

    private Button buttonPane2;
    private Button buttonPane3;
    private Button buttonPane4;
    private Button buttonPane5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        buttonPanel = findViewById(R.id.buttonPanel);
        buttonPanel.setOnClickListener(this);
        buttonPane2 = findViewById(R.id.buttonPane2);
        buttonPane2.setOnClickListener(this);
        buttonPane3 = findViewById(R.id.buttonPane3);
        buttonPane3.setOnClickListener(this);
        buttonPane4 = findViewById(R.id.buttonPane4);
        buttonPane4.setOnClickListener(this);
        buttonPane5 = findViewById(R.id.buttonPane5);
        buttonPane5.setOnClickListener(this);
        popupWindow = new CommonPopupWindow.Builder(PopupActivity.this)
                .setView(R.layout.popup_item_one)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//???????????????????????????
                .setViewOnclickListener(this)
                .setAnimationStyle(R.style.MainPopAnim_top)//??????popWindow???????????????
                .create();

//        popupWindow2 = new CommonPopupWindow.Builder(PopupActivity.this)
//                .setView(R.layout.popup_item_one)
//                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setOutsideTouchable(true)//???????????????????????????
//                .setAnimationStyle(R.style.MainPopAnim_top)//??????popWindow???????????????
//                .create();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPanel://????????????
                popupWindow.setAnimationStyle(R.style.MainPopAnim_top);
                popupWindow.showUpView(buttonPanel, 0.5f);
                break;

            case R.id.buttonPane2://????????????
                popupWindow.setAnimationStyle(R.style.MainPopAnim_bottom);
                popupWindow.showDownView(buttonPane2, 0.5f);
                break;

            case R.id.buttonPane3://????????????
                popupWindow.setAnimationStyle(R.style.MainPopAnim_left);
                popupWindow.showLeftView(buttonPane3, 0.5f);
                break;

            case R.id.buttonPane4://????????????
                popupWindow.setAnimationStyle(R.style.MainPopAnim_right);
                popupWindow.showRightView(buttonPane4, 0.5f);
                break;
            case R.id.buttonPane5:
                selectHuny(buttonPane5);
                break;
        }
    }


    @Override
    public void getChildView(View view) {
        TextView text_hunzi = view.findViewById(R.id.text_hunzi);
        text_hunzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void selectHuny(View relativeLayout) {
        String[] data = getResources().getStringArray(R.array.medical);
        View contentView = getLayoutInflater().inflate(R.layout.pw_item, null);
        WrapListView lv = (WrapListView) contentView.findViewById(R.id.lv_pw);
        lv.setListViewHeight((int) getResources().getDimension(R.dimen.x300));//??????????????????

//        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_list_item_leo, data);
        PowWindowAdapter mAdapter = new PowWindowAdapter(PopupActivity.this);
        mAdapter.setList(data);
        lv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final PopupWindow mPopupWindow = new PopupWindow(contentView, relativeLayout.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // ???????????????PopupWindow????????????????????????????????????????????????????????????????????????????????????Back????????????dismiss??????
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // ????????????????????????show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPoss(relativeLayout, contentView);
        mPopupWindow.showAtLocation(relativeLayout, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);

        final String[] finalData = data;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = finalData[position];
                mPopupWindow.dismiss();
                //??????
                MyToast.show(item);
            }
        });
    }

}
