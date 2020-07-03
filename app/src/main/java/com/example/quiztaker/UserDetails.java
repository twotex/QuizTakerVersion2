
package com.example.quiztaker;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {
    private String xUsername;
    private int xAge;

    public UserDetails(String uName, int uAge) {
        xUsername = uName;
        xAge = uAge;
    }

    public String getUsername() {
        return xUsername;
    }

    public int getAge() {
        return xAge;
    }

    protected UserDetails(Parcel in) {
        xUsername = in.readString();
        xAge = in.readInt();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(xUsername);
        dest.writeInt(xAge);
    }
}


