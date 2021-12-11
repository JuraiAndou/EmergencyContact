package com.example.emergencycontacts.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;

import androidx.fragment.app.DialogFragment;

public class UIEducacionalPermissao extends DialogFragment {

    String msg;
    String ttl;
    int code; //Código para o dialog
    public UIEducacionalPermissao(String menssagem, String titulo, int codigo){
        this.msg = menssagem;
        this.ttl = titulo;
        this.code = codigo;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.msg).setTitle(this.ttl);
        builder.setPositiveButton("OK", (dialog, which) -> {
            listener.onDialogPositiveClick(code);
        });
        AlertDialog adialog = builder.create();
        return adialog;
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int codigo);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        }catch (ClassCastException e){
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
