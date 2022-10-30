package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginPageFragment extends Fragment {

    TextView sign_in_btn;
    Button login_in_btn;
    EditText usernameInput, passwordInput;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginPageFragment newInstance(String param1, String param2) {
        LoginPageFragment fragment = new LoginPageFragment();
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