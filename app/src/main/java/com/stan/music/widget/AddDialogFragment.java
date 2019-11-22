package com.stan.music.widget;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.stan.music.R;
import com.stan.music.utils.InputUtil;

/**
 * Author: Stan
 * Date: 2019/11/18 11:30
 * Description: ${DESCRIPTION}
 */
public class AddDialogFragment extends BaseDialogCenterFragment implements View.OnClickListener {
    private EditText editInput;
    private ImageView ivDelete;
    private CheckBox cbSetting;
    private TextView tvCount;
    private TextView tvSubmit;
    private TextView tvDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.add_dialog_layout, container);
        initView(view);
        setViewListener();


        return view;
    }

    private void setViewListener() {
        ivDelete.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint({"SetTextI18n"})
            @Override
            public void afterTextChanged(Editable s) {
                tvCount.setText(s.toString().length()+ "/40");
                if(s.toString().isEmpty()){
                    ivDelete.setVisibility(View.INVISIBLE);
                    tvSubmit.setTextColor(getResources().getColor(R.color.colorTabBarDark));
                }else{
                    ivDelete.setVisibility(View.VISIBLE);
                    tvSubmit.setTextColor(getResources().getColor(R.color.colorTabBar));
                }
            }
        });
    }

    private void initView(View view) {
        editInput = view.findViewById(R.id.et_input_title);
        ivDelete = view.findViewById(R.id.iv_delete);
        cbSetting = view.findViewById(R.id.cb_setting);
        tvCount = view.findViewById(R.id.tv_count);
        tvSubmit = view.findViewById(R.id.tv_submit);
        tvDelete = view.findViewById(R.id.tv_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                editInput.setText(null);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                //提交
                String editTitle = editInput.getText().toString();
                if(InputUtil.checkInputLegel(editTitle,40)){
                    listener.submit(editTitle,cbSetting.isChecked());
                    dismiss();
                }
                break;
        }

    }
    private OnDialogClickListener listener;
    public interface OnDialogClickListener{
        void submit(String title,boolean isCheck);
    }
    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.listener = listener;
    }

}
