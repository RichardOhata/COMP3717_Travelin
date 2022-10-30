package com.example.travelin;

import android.graphics.Color;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPageFragment extends Fragment {

    TextView login_btn;
    Button sign_up_btn;
    EditText usernameInput, emailInput, passwordInput;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpPageFragment newInstance(String param1, String param2) {
        SignUpPageFragment fragment = new SignUpPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                // Sign up user
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

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