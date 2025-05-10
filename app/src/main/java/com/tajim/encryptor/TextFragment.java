package com.tajim.encryptor;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextFragment extends Fragment {

    Boolean encrypt = true;
    TextInputEditText ed_msg, ed_pass;
    Button btn_main;
    TextView txt_result, txt_copy, caution_txt;
    TabLayout tab_lay;
    TextInputLayout edLay_msg, edLay_pass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        ed_msg = view.findViewById(R.id.ed_msg_txt);
        ed_pass = view.findViewById(R.id.ed_pass_txt);
        edLay_pass = view.findViewById(R.id.edLay_pass_txt);
        btn_main = view.findViewById(R.id.btn_main_txt);
        txt_result = view.findViewById(R.id.txt_result_txt);
        tab_lay = view.findViewById(R.id.tab_lay_txt);
        caution_txt = view.findViewById(R.id.caution_txt);
        edLay_msg = view.findViewById(R.id.edLay_msg_txt);
        txt_copy = view.findViewById(R.id.txt_copy_txt);
        txt_copy.setOnClickListener(v -> {
            String textToCopy = txt_result.getText().toString();
            MyMethods.CopyClip(getActivity(), textToCopy);


        });
        tab_lay.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                caution_txt.setVisibility(View.VISIBLE);
                caution_txt.setVisibility(View.VISIBLE);
                ed_msg.setText("");
                ed_pass.setText("");
                ed_pass.clearFocus();
                ed_msg.clearFocus();
                txt_copy.setVisibility(View.GONE);
                edLay_pass.setError(null);
                edLay_pass.setErrorIconDrawable(null);
                edLay_pass.setHelperText("");
                txt_result.setText(" ");
                int itemid = tab.getPosition();
                if (itemid == 0){
                    edLay_msg.setHint("Enter Text");

                    tab_lay.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue));
                    btn_main.setBackgroundColor(getResources().getColor(R.color.blue));
                    btn_main.setText("Encrypt");
                    encrypt = true;
                }else{
                    edLay_msg.setHint("Encrypted Key");
                    tab_lay.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
                    btn_main.setBackgroundColor(getResources().getColor(R.color.red));
                    btn_main.setText("Decrypt");
                    encrypt = false;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_main.setOnClickListener(v->{
            String text= ed_msg.getText().toString();
            String pass = ed_pass.getText().toString();
            if (text.length()>0){

                if (pass.length()!=6){
                    ed_pass.requestFocus();
                    ed_pass.setError("Only 6 char.");
                }else {
                    caution_txt.setVisibility(View.GONE);
                    txt_copy.setVisibility(View.VISIBLE);
                    if (encrypt) {

                        try {
                            String result = MyMethods.encryptData(text, pass);
                            txt_result.setText(result);
                            MyMethods.CopyClip(getActivity(), result);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        txt_copy.setText("Encrypted Key:");

                    }else {


                        try {
                            String result = MyMethods.decryptData(text, pass);
                            txt_result.setText(result);
                            MyMethods.CopyClip(getActivity(), result);
                            txt_copy.setText("Decrypted Text:");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                }}else{
                Toast.makeText(getActivity(), "Enter all data", Toast.LENGTH_SHORT).show();
            }
        });

        ed_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length()!=6){
                    edLay_pass.setError("* 6 char. pass");



                }else {
                    edLay_pass.setError(null);  // Clear the error message
                    edLay_pass.setHelperText("* Looks Great !");  // Display the success message
                    edLay_pass.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));  // Set the helper text color to green
                    edLay_pass.setBoxStrokeColor(getResources().getColor(R.color.green));
                    edLay_pass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}