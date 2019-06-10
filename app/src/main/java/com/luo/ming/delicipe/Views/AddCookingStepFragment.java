package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.luo.ming.delicipe.Models.UserRecipeStep;
import com.luo.ming.delicipe.Presenters.AddCookingStepPresenter;
import com.luo.ming.delicipe.R;

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

        final Button addphotoBtn = row.findViewById(R.id.button_add_photo);

        TextView testStepNum = row.findViewById(R.id.textStepNum);
        int rowCount = tableLayout.getChildCount();
        testStepNum.setText("Step "+String.valueOf(rowCount+1));
        tableLayout.addView(row,rowCount);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Uri selectedImage = data.getData();
            View row = tableLayout.getChildAt(requestCode);
            ImageView stepImage = row.findViewById(R.id.imageStep);
            stepImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            stepImage.setImageURI(selectedImage);
            imageUriList.add(requestCode,selectedImage.toString());
        }

    }

    public void saveCookingStepInfo(){

        ArrayList<UserRecipeStep> userRecipeStepsList = new ArrayList<>();

        int rowCount = tableLayout.getChildCount();
        for(int i=0;i<rowCount;i++){

            View view = tableLayout.getChildAt(i);

            EditText stepText = view.findViewById(R.id.textStep);

            String step = stepText.getText().toString();


            UserRecipeStep userRecipeStep = new UserRecipeStep(imageUriList.get(i),step);
            userRecipeStepsList.add(userRecipeStep);

        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEP_INFO_BUNDLE, userRecipeStepsList);

        listener.onAddCookingStepFragmentInteraction(bundle);

    }

    public interface OnAddCookingStepFragmentInteractionListener{
        void onAddCookingStepFragmentInteraction(Bundle bundle);
    }
}
