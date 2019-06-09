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
import android.widget.ImageView;

import com.luo.ming.delicipe.Models.RecipeCover;
import com.luo.ming.delicipe.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCoverFragment extends Fragment {

    private Uri imageUri;

    private ImageView coverImage;
    private Button addBtn;
    private OnAddCoverFragmentInteractionListener listener;
    private RecipeCover recipeCover;
    public final static String COVER_INFO_BUNDLE_TAG= "com.luo.ming.delicipe.Views.AddCoverFragment";


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

        coverImage = view.findViewById(R.id.imageCover);
        addBtn = view.findViewById(R.id.addImageButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(pickPhoto,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageUri = selectedImage;
                    coverImage.setImageURI(selectedImage);
                    coverImage.setAdjustViewBounds(true);
                    addBtn.setVisibility(View.INVISIBLE);
                }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnAddCoverFragmentInteractionListener){
            listener = (OnAddCoverFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()+
                    "must implement OnAddCoverFragmentInteractionListener");
        }
    }

    public void saveCoverPageInfo(){

        recipeCover = new RecipeCover("uri sample","cover name",2,2,"my recipe");
        Bundle bundle = new Bundle();
        bundle.putParcelable(COVER_INFO_BUNDLE_TAG,recipeCover);

        listener.onAddCoverFragmentInteraction(bundle);

    }


    public interface OnAddCoverFragmentInteractionListener {

        void onAddCoverFragmentInteraction(Bundle bundle);
    }
}
