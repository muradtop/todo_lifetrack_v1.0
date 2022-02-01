package com.example.todo_lifetrack.fragment.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo_lifetrack.R;
import com.example.todo_lifetrack.databinding.FragmentFinishRegistrationBinding;


public class FinishRegistrationFragment extends Fragment {
    private FragmentFinishRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFinishRegistrationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickers();
    }

    private void initClickers() {
        binding.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
            }
        });

    }
}