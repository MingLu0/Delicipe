package com.luo.ming.delicipe.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.luo.ming.delicipe.R;

//class AlertDialogFragment extends DialogFragment {
//
//    public static AlertDialogFragment newInstance(int title, int message){
//
//        AlertDialogFragment fragment = new AlertDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("title",title);
//        bundle.putInt("message",message);
//        fragment.setArguments(bundle);
//        return fragment;
//
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        int title = getArguments().getInt("title");
//        int message = getArguments().getInt("message");
//
//        return new AlertDialog.Builder(getActivity())
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        ((FragmentAlertDialog)getActivity()).doNegativeClick();
//                    }
//                })
//                .create();
//    }
//}
