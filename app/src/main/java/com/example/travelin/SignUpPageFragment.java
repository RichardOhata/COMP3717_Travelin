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
 * SignUpPageFragment.
 */
public class SignUpPageFragment extends Fragment {

    TextView login_btn;
    Button sign_up_btn;
    EditText usernameInput, emailInput, passwordInput;


    public SignUpPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_page, container, false);
        login_btn = view.findViewById(R.id.log_in_text_btn_textView);
        login_btn.setOnClickListener(btn -> ((LoginPage) requireActivity()).switchFragments(false));

        sign_up_btn = view.findViewById(R.id.signup_page_signup_button);

        sign_up_btn.setOnClickListener(btn -> {
            if (checkFields(view)) {
                // Signs up user
                ((LoginPage) requireActivity()).addUser(usernameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString());
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
     *          else stay on SignUpPageFragment
     */
    public boolean checkFields(View view) {
        usernameInput = view.findViewById(R.id.signup_username_editText);
        emailInput = view.findViewById(R.id.signup_email_editText);
        passwordInput = view.findViewById(R.id.signup_password_editText);

        if (TextUtils.isEmpty(usernameInput.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter a username", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(emailInput.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter a email", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}