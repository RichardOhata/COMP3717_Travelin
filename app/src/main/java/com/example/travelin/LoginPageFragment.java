package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LoginPageFragment.
 */
public class LoginPageFragment extends Fragment {

    TextView sign_in_btn;
    Button login_in_btn;
    EditText usernameInput, passwordInput;


    public LoginPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);

        sign_in_btn = view.findViewById(R.id.sign_up_text_btn_textView);
        login_in_btn = view.findViewById(R.id.login_page_login_button);

        sign_in_btn.setOnClickListener(btn -> ((LoginPage) requireActivity()).switchFragments(true));

        login_in_btn.setOnClickListener(btn -> {
            if (checkFields(view)) {
                // Logs in user
                ((LoginPage) requireActivity()).login(usernameInput.getText().toString(), passwordInput.getText().toString());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Checks EditText fields to see if they are all filled.
     *
     * @param view view
     * @return true if fields are all filled
     *          else stay on LoginPageFragment
     */
    public boolean checkFields(View view) {
        usernameInput = view.findViewById(R.id.login_username_editText);
        passwordInput = view.findViewById(R.id.login_password_editText);

        if (TextUtils.isEmpty(usernameInput.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter a username", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}