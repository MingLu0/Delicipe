package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Presenters.AddCoverFragmentPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCoverFragment extends Fragment implements AddCoverFragmentPresenter.FragmentView{


    private String imageUri;
    private ImageView coverImageButton;
    private Button addBtn;
    private OnAddCoverFragmentInteractionListener listener;
    private UserRecipeCover userRecipeCover;
    public final static String COVER_INFO_BUNDLE_TAG= "com.luo.ming.delicipe.Views.AddCoverFragment";
    private AddCoverFragmentPresenter presenter;

    private TextInputLayout name_layout, cooking_time_layout, serving_size_layout, comment_layout;
    private TextInputEditText name_text, cooking_time_text, serving_size_text, comment_text;


    private byte[] imageBytes;


    public AddCoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name_layout = view.findViewById(R.id.input_layout_recipe_name);
        cooking_time_layout = view.findViewById(R.id.input_layout_cooking_time);
        serving_size_layout = view.findViewById(R.id.input_layout_servings);
        comment_layout = view.findViewById(R.id.input_layout_comment) ;


        cooking_time_text = view.findViewById(R.id.input_text_cooking_time);
        serving_size_text = view.findViewById(R.id.input_edit_text_servings);
        comment_text = view.findViewById(R.id.input_edit_text_comment);
        name_text = view.findViewById(R.id.input_text_recipe_name);

        coverImageButton = view.findViewById(R.id.imageCover);

        coverImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent pickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(pickPhoto,1);
            }
        });

        presenter = new AddCoverFragmentPresenter(getActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   imageBytes = BitmapUtility.convertBitmapToBytes(bitmap);

                   Log.d("AddCoverFragment",String.valueOf(imageBytes.length));

                    Picasso.with(getActivity())
                            .load(selectedImage)
                            .fit()
                            .into(coverImageButton);

                }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnAddCoverFragmentInteractionListener){
            listener = (OnAddCoverFragmentInteractionListener)context;
        } else {
            throw new RuntimeException();
        }
    }




    @Override
    public byte[] getUserImage() {
        return imageBytes;
    }

    @Override
    public String getCoverName() {

        if(!TextUtils.isEmpty(name_text.getText())){
            return name_text.getText().toString().trim();
        }

        showNameEmptyError();
        return null;
    }

    @Override
    public int getCookingTime() {
        if(!TextUtils.isEmpty(cooking_time_text.getText())) {
            return Integer.valueOf(cooking_time_text.getText().toString());
        }
        return 0;
    }

    @Override
    public int getServingSize() {
        if(!TextUtils.isEmpty(serving_size_text.getText())){
            return Integer.valueOf(serving_size_text.getText().toString());
        }
        return 0;

    }

    @Override
    public String getComment() {
        if(!TextUtils.isEmpty(comment_text.getText())){
            return comment_text.getText().toString().trim();
        }
        return null;
    }

    @Override
    public void showNameExistsError() {
        name_layout.setError("Name already exists, please enter another one");
    }

    @Override
    public void showNameEmptyError() {

        name_layout.setError("Please Enter Recipe Name");
    }

    public void sendDataBackToActivity(){

        userRecipeCover = presenter.getCoverInfoFromInput();
        Bundle bundle = new Bundle();
        bundle.putParcelable(COVER_INFO_BUNDLE_TAG, userRecipeCover);
        listener.onAddCoverFragmentInteraction(bundle);
    }

    public interface OnAddCoverFragmentInteractionListener {

        void onAddCoverFragmentInteraction(Bundle bundle);
    }
}
