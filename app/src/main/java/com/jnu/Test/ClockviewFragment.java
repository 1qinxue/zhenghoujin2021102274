package com.jnu.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class ClockviewFragment extends Fragment {


    public EditText editTextTime2;
    public ClockviewFragment() {
        // Required empty public constructor
    }

    public static ClockviewFragment newInstance(String param1, String param2) {
        ClockviewFragment fragment = new ClockviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clockview, container, false);
        // Inflate the layout for this fragment
        return view;
    }
}
