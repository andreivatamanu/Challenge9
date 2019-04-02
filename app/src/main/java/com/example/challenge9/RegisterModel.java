package com.example.challenge9;

public class RegisterModel {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;
    private boolean mChecked;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "First name='" + mFirstName + '\'' +
                ", Last name='" + mLastName + '\'' +
                ", Email='" + mEmail + '\'' +
                ", Password='" + mPassword + '\'' +
                ", Agree status=" + mChecked +
                '}';
    }
}
