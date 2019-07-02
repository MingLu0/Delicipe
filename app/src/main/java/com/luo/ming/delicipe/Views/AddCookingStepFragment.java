package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeStep;
import com.luo.ming.delicipe.Presenters.AddCookingStepPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCookingStepFragment extends Fragment implements AddCookingStepPresenter.View {

    private TableLayout tableLayout;
    private Button addStepButton;
    private AddCookingStepPresenter presenter;
    private OnAddCookingStepFragmentInteractionListener listener;

    private ArrayList<String>imageUriList;
    private ArrayList<byte[]>imageBytesList;

    public static final String STEP_INFO_BUNDLE = "package com.luo.ming.delicipe." +
            "Views.AddCookingStepFragment";

    private int selectedRow;


    public AddCookingStepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnAddCookingStepFragmentInteractionListener){
            listener = (OnAddCookingStepFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()+
                    "must implement OnAddIngredientStepFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cook_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageUriList = new ArrayList<>();
        imageBytesList = new ArrayList<>();
        tableLayout = view.findViewById(R.id.tableLayout_steps);
        addStepButton = view.findViewById(R.id.add_step_button);

        presenter = new AddCookingStepPresenter(getActivity(),this);
        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.displayTableLayout();
            }
        });
    }

    @Override
    public void displayTableLayout() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row_step,null);

        ImageView addphotoBtn = row.findViewById(R.id.imageStep);
        ImageButton deleteButton = row.findViewById(R.id.delete_step_button);

        TextInputLayout stepTextLayout = row.findViewById(R.id.input_layout_step);
        TextInputEditText stepText = row.findViewById(R.id.input_edit_text_step);

        int rowCount = tableLayout.getChildCount();
        tableLayout.addView(row,rowCount);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View row = (View)v.getParent();
                ViewGroup viewGroup = (ViewGroup)row.getParent();
                viewGroup.removeView(row);

            }
        });

        addphotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int requestCode = tableLayout.indexOfChild(row);
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto,requestCode);
            }


        });
    }


    @Override
    public ArrayList<UserRecipeStep> getStepsFromTableLayout() {

        ArrayList<UserRecipeStep>userRecipeStepList = new ArrayList<>();

        int rowCount = tableLayout.getChildCount();
        for(int i=0;i<rowCount;i++){
            View view = tableLayout.getChildAt(i);

            TextInputEditText stepText = view.findViewById(R.id.input_edit_text_step);
            ImageView stepImage = view.findViewById(R.id.imageStep);
            String textStep = null;

            if(!TextUtils.isEmpty(stepText.getText())){
                textStep = stepText.getText().toString();
            }

            Bitmap bitmap = null;
            byte[] imageBytes = null;

            if(stepImage.getDrawable()!=null){
                bitmap = ((BitmapDrawable)stepImage.getDrawable()).getBitmap();
                imageBytes = BitmapUtility.convertBitmapToBytes(bitmap);
            }

            UserRecipeStep userRecipeStep = new UserRecipeStep(imageBytes,textStep);

            userRecipeStepList.add(userRecipeStep);

        }
        return userRecipeStepList;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(resultCode == RESULT_OK){

                Uri selectedImage = data.getData();
                View row = tableLayout.getChildAt(requestCode);
                ImageView stepImage = row.findViewById(R.id.imageStep);

                Picasso.with(getActivity())
                            .load(selectedImage)
                            .fit()
                            .into(stepImage);
            }


    }



    public void sendDataBackToActivity(){

        ArrayList<UserRecipeStep> userRecipeStepsList = presenter.getUserSteps();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEP_INFO_BUNDLE, userRecipeStepsList);
        listener.onAddCookingStepFragmentInteraction(bundle);

    }

    public interface OnAddCookingStepFragmentInteractionListener{
        void onAddCookingStepFragmentInteraction(Bundle bundle);
    }
}
