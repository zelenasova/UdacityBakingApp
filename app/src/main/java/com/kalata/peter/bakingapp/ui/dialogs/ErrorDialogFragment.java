package com.kalata.peter.bakingapp.ui.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.kalata.peter.bakingapp.R;


public class ErrorDialogFragment extends DialogFragment {

    private final static String ARG_MSG= "arg_message";

    public static ErrorDialogFragment newInstance(String message) {
        ErrorDialogFragment frag = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MSG, message);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(ARG_MSG))
                .setTitle(R.string.error)
                .setPositiveButton(R.string.ok, (dialog, id) -> {});
        return builder.create();
    }
}
