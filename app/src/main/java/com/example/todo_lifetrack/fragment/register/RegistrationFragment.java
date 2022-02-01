package com.example.todo_lifetrack.fragment.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.todo_lifetrack.R;
import com.example.todo_lifetrack.databinding.FragmentRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegistrationFragment extends Fragment {

    private static final String TAG = "PhoneAuthActivity";
    private FragmentRegistrationBinding binding;


    private FirebaseAuth mAuth;

    private String mVerificationId;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickers();

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
            }
        };
    }

    private void initClickers() {
        binding.shedNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = binding.numberEt.getText().toString();
                startPhoneNumberVerification(number);
            }
        });
        binding.shedCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.codeEt.getText().toString();
                verifyPhoneNumberWithCode(mVerificationId,code);
            }
        });
    }

    @Override
    public void onStart() {
            super.onStart();


        }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)  // Номер телефона для подтверждения
                        .setTimeout(60L, TimeUnit.SECONDS)// Время ожидания и единица измерения
                        .setActivity(requireActivity())// Действие (для привязки обратного вызова)
                        .setCallbacks(mCallbacks)// Проверка состояния измененных обратных вызовов
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if ( currentUser != null ) {
                            Navigation.findNavController(requireView()).navigate(R.id.finishRegistrationFragment);
                        }else if (!task.isSuccessful()) {
                            Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                            Log.d(TAG, "signInWithCredential:success");
                            // Обновление пользовательского интерфейса
                        }

                    }
                });
    }

}

