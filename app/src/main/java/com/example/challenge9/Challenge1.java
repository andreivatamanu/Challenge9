package com.example.challenge9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Challenge1 extends AppCompatActivity {

    public static final String FIRST_NAME = "first name";
    public static final String LAST_NAME = "last name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CHECKED = "checked";
    public static final String FILENAME = "filename.txt";

    private SharedPreferences mSharedPreferences;
    private RegisterModel mRegisterModel;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private TextView mTextViewDisplayData;
    private TextView mTextViewErrorCheckbox;
    private CheckBox mCheckBoxAgree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge1);
        initSharedPreferences();
        initRegisterModel();
        initView();


    }

    private void initSharedPreferences() {
        mSharedPreferences = this.getSharedPreferences(ApplicationData.APP_KEY,
                Context.MODE_PRIVATE);
    }

    private void initRegisterModel() {
        mRegisterModel = new RegisterModel();
    }

    private void initView() {
        mEditTextFirstName = findViewById(R.id.editText_firstName);
        mEditTextLastName = findViewById(R.id.editText_lastName);
        mEditTextEmail = findViewById(R.id.editText_email);
        mEditTextPassword = findViewById(R.id.editText_password);
        mTextViewDisplayData = findViewById(R.id.textView_displayData);
        mTextViewErrorCheckbox = findViewById(R.id.textView_checkBoxError);
        mTextViewErrorCheckbox.setVisibility(View.INVISIBLE);
        mCheckBoxAgree = findViewById(R.id.checkBox_terms);
        mCheckBoxAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTextViewErrorCheckbox.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void onClickSave(View view) {

        String firstName = mEditTextFirstName.getText().toString();
        String lastName = mEditTextLastName.getText().toString();
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        boolean isChecked = mCheckBoxAgree.isChecked();
        String regex = "^(([a-zA-Z]+(\\s|-|'|\\.))|([a-zA-Z]+)|([a-zA-Z]+(([.'])\\s)))+$";

        mTextViewDisplayData.setText(R.string.empty);

        if (!firstName.matches(regex)) {
            mEditTextFirstName.setError("Insert a valid first name");
        }

        if (!lastName.matches(regex)) {
            mEditTextLastName.setError("Insert a valid last name");
        }

        if (!isValidEmail(email)) {
            mEditTextEmail.setError("Insert a valid email");
        }

        if (password.isEmpty()) {
            mEditTextPassword.setError("Insert a password");
        }

        if (!isChecked) {
            mTextViewErrorCheckbox.setVisibility(View.VISIBLE);
        }

        if (firstName.matches(regex) && lastName.matches(regex) && isValidEmail(email) && !password.isEmpty() && isChecked) {
            ApplicationData.setStringValueInSharedPreferences(this, FIRST_NAME,
                    firstName);
            ApplicationData.setStringValueInSharedPreferences(this, LAST_NAME,
                    lastName);
            ApplicationData.setStringValueInSharedPreferences(this, EMAIL,
                    email);
            ApplicationData.setStringValueInSharedPreferences(this, PASSWORD,
                    password);
            ApplicationData.setBooleanValueInSharedPreferences(this, CHECKED,
                    true);

            mRegisterModel.setFirstName(firstName);
            mRegisterModel.setLastName(lastName);
            mRegisterModel.setEmail(email);
            mRegisterModel.setPassword(password);
            mRegisterModel.setChecked(true);

            String fileContents = mRegisterModel.toString();

            FileOutputStream fileOutputStream;

            try {
                fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write("Internal: " + fileContents);
                outputStreamWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isExternalStorageWritable()) {
                try {
                    File file = new File(this.getExternalFilesDir(null), FILENAME);
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutput);
                    outputStreamWriter.write("External: " + fileContents);
                    outputStreamWriter.flush();
                    fileOutput.getFD().sync();
                    outputStreamWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            setEmpty();
        }
    }


    public void onClickShow(View view) {

        removeError();

        mRegisterModel.setFirstName(ApplicationData.getStringValueFromSharedPreferences(this,
                FIRST_NAME));
        mEditTextFirstName.setText(mRegisterModel.getFirstName());

        mRegisterModel.setLastName(ApplicationData.getStringValueFromSharedPreferences(this,
                LAST_NAME));
        mEditTextLastName.setText(mRegisterModel.getLastName());

        mRegisterModel.setEmail(ApplicationData.getStringValueFromSharedPreferences(this, EMAIL));
        mEditTextEmail.setText(mRegisterModel.getEmail());

        mRegisterModel.setPassword(ApplicationData.getStringValueFromSharedPreferences(this,
                PASSWORD));
        mEditTextPassword.setText(mRegisterModel.getPassword());

        mRegisterModel.setChecked(ApplicationData.getBooleanValueFromSharedPreferences(this,
                CHECKED));
        mCheckBoxAgree.setChecked(mRegisterModel.isChecked());


        if (mSharedPreferences.contains(FIRST_NAME)) {
            mTextViewDisplayData.setText(mRegisterModel.toString());
        } else {
            mTextViewDisplayData.setText(getString(R.string.Empty));
        }

    }

    public void onClickClear(View view) {
        mTextViewDisplayData.setText(getString(R.string.empty));
        ApplicationData.deleteAllValuesFromSharedPreferences(this);
    }

    private void removeError() {
        mEditTextFirstName.setError(null);
        mEditTextLastName.setError(null);
        mEditTextEmail.setError(null);
        mEditTextPassword.setError(null);
        mTextViewErrorCheckbox.setVisibility(View.INVISIBLE);
    }

    private void setEmpty() {
        mEditTextFirstName.setText("");
        mEditTextLastName.setText("");
        mEditTextEmail.setText("");
        mEditTextPassword.setText("");
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void onClickReadFromInternal(View view) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = this.openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[100];
            StringBuilder stringBuilder = new StringBuilder();
            int charRead;
            while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                stringBuilder.append(String.copyValueOf(inputBuffer, 0, charRead));
            }
            inputStreamReader.close();
            mTextViewDisplayData.setText(stringBuilder);
        } catch (IOException e) {
            mTextViewDisplayData.setText(getText(R.string.Empty));
        }

    }

    public void onClickReadFromExternal(View view) {

        File file = new File(this.getExternalFilesDir(null), FILENAME);


        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[100];
            StringBuilder stringBuilder = new StringBuilder();
            int charRead;
            while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                stringBuilder.append(String.copyValueOf(inputBuffer, 0, charRead));
            }
            inputStreamReader.close();
            mTextViewDisplayData.setText(stringBuilder);
        } catch (IOException e) {
            mTextViewDisplayData.setText(getText(R.string.Empty));
        }
    }
}
