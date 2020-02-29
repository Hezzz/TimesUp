package com.example.timesup_final_project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;


public class MyDialogFragment extends AppCompatDialogFragment {

    EditText addTitle, addDesc;
    TextView cancel, addNew;

    public interface OnInputSelected{
        void sendInput(String title, String desc);
    }
    public OnInputSelected onInputSelected;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        addTitle = view.findViewById(R.id.titleEditText);
        addDesc = view.findViewById(R.id.descEditText);
        cancel = view.findViewById(R.id.cancel);
        addNew = view.findViewById(R.id.okay);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = addTitle.getText().toString();
                String desc = addDesc.getText().toString();
                if(!input.isEmpty() || !desc.isEmpty()){
                    Toast.makeText(getContext(), "Added " + input, Toast.LENGTH_SHORT).show();
//                    HomeFragment fragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag(null);
//                    fragment.message = input;
                    onInputSelected.sendInput(input, desc);
                }
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSelected = (OnInputSelected)getTargetFragment();
        }catch(ClassCastException e){

        }
    }
}
