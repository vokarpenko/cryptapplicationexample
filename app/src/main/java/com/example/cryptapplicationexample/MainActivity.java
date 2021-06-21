package com.example.cryptapplicationexample;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SAMPLE_ALIAS = "ALIAS";
    private EditText edTextToEncrypt;
    private TextView tvEncryptedText;
    private TextView tvDecryptedText;
    private EnCryptor encryptor;
    private DeCryptor decryptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDecryptedText = findViewById(R.id.tv_decrypted_text);
        tvEncryptedText = findViewById (R.id.tv_encrypted_text);
        edTextToEncrypt = findViewById (R.id.ed_text_to_encrypt);
        Button btnEncrypt = findViewById(R.id.btn_encrypt);
        Button btnDecrypt = findViewById(R.id.btn_decrypt);
        encryptor = new EnCryptor();
        try {
            decryptor = new DeCryptor();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException |
                IOException e) {
            e.printStackTrace();
        }
        btnEncrypt.setOnClickListener(v -> encryptText());
        btnDecrypt.setOnClickListener(v -> decryptText());
    }

    private void decryptText() {
        try {
            tvDecryptedText.setText(decryptor
                    .decryptData(SAMPLE_ALIAS, encryptor.getEncryption(), encryptor.getIv()));
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException |
                KeyStoreException | NoSuchPaddingException |
                IOException | InvalidKeyException e) {
            Log.e(TAG, "decryptData() called with: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private void encryptText() {
        try {
            final byte[] encryptedText = encryptor
                    .encryptText(SAMPLE_ALIAS, edTextToEncrypt.getText().toString());
            tvEncryptedText.setText(Base64.encodeToString(encryptedText, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException | NoSuchProviderException |
                IOException | NoSuchPaddingException | InvalidKeyException e) {
            Log.e(TAG, "onClick() called with: " + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }
}