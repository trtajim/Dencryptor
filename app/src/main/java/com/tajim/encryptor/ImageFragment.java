package com.tajim.encryptor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class ImageFragment extends Fragment {

    TabLayout tab_img;
    Button  submit_btn_img;
    ImageView img_img,  result_img;
    TextInputEditText  ed_msg_img;
    TextInputLayout edLay_msg_img;
    TextView txt_copy_img, txt_result_img, caution_img,add_image;
    RelativeLayout rl_img;
    Boolean encrypt = true;
    String texttocopy = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        tab_img = view.findViewById(R.id.tab_lay_img);
        add_image = view.findViewById(R.id.add_img);
        txt_copy_img = view.findViewById(R.id.txt_copy_img);
        rl_img = view.findViewById(R.id.rl_img);
        txt_result_img = view.findViewById(R.id.txt_result_img);
        caution_img = view.findViewById(R.id.caution_img);
        result_img = view.findViewById(R.id.result_img);
        submit_btn_img = view.findViewById(R.id.submit_btn_img);
        img_img = view.findViewById(R.id.img_img);
        ed_msg_img = view.findViewById(R.id.ed_msg_img);
        edLay_msg_img = view.findViewById(R.id.edLay_msg_img);

        txt_copy_img.setOnClickListener(v->{MyMethods.CopyClip(getActivity(), texttocopy);});

        tab_img.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ed_msg_img.setText("");
                caution_img.setVisibility(View.GONE);
                txt_copy_img.setText("");
                txt_copy_img.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                txt_result_img.setVisibility(View.INVISIBLE);
                ed_msg_img.clearFocus();



                int itemid = tab.getPosition();
                if (itemid == 0){
                    encrypt = true;

                    tab_img.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue));
                    submit_btn_img.setBackground(getResources().getDrawable(R.drawable.curved_blue));
                    rl_img.setVisibility(View.VISIBLE);
                    edLay_msg_img.setVisibility(View.GONE);
                    result_img.setVisibility(View.GONE);
                    submit_btn_img.setText("Encrypt");


                }else{
                    caution_img.setVisibility(View.VISIBLE);

                    encrypt = false;
                    tab_img.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
                    submit_btn_img.setBackground(getResources().getDrawable(R.drawable.curved_red));
                    edLay_msg_img.setVisibility(View.VISIBLE);
                    rl_img.setVisibility(View.GONE);

                    submit_btn_img.setText("Decrypt");



                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        submit_btn_img.setOnClickListener(v->{




                if (encrypt){
                    try {

                        String result = MyMethods.encryptImg(img_img);
                        txt_copy_img.setText("Encrypted Key: ");
                        txt_copy_img.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_content_copy_24,0,0,0);
                        txt_result_img.setVisibility(View.VISIBLE);
                        MyMethods.CopyClip(getActivity(), result);
                        texttocopy = result;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                }else {
                    ed_msg_img.clearFocus();

                    if (ed_msg_img.getText().toString().length() != 0){
                        caution_img.setVisibility(View.GONE);


                        try {
                            result_img.setImageBitmap(MyMethods.decryptImage(ed_msg_img.getText().toString()));
                            ed_msg_img.setText("");
                            txt_copy_img.setText("Decrypted Img: ");
                            txt_copy_img.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            result_img.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        ed_msg_img.requestFocus();
                        ed_msg_img.setError("Enter Key");

                    }
                }

        });


        add_image.setOnClickListener(v->{
           ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1024, 1024)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickerLauncher.launch(intent);

                            return null;
                        }
                    });

        });



        return view;
    }

    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                     if (o.getResultCode()== MainActivity.RESULT_OK){

                         Intent intent = o.getData();
                         Uri uri = intent.getData();
                         try {
                             Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(),uri);
                             img_img.setImageBitmap(bitmap);
                         } catch (IOException e) {
                             throw new RuntimeException(e);
                         }


                     }else {

                     }
                }
            });
}