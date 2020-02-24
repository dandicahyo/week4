package id.ac.polinema.intentexercise;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private String fullname;
    private String email;
    private String aboutYou;
    private String homePage;
    private Bitmap profileImage;

    public UserModel(String fullname, String email, String aboutYou, String homePage, Bitmap profileImage) {
        this.fullname = fullname;
        this.email = email;
        this.aboutYou = aboutYou;
        this.homePage = homePage;
        this.profileImage = profileImage;
    }

    protected UserModel(Parcel in) {
        fullname = in.readString();
        email = in.readString();
        aboutYou = in.readString();
        homePage = in.readString();
        profileImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getAboutYou() {
        return aboutYou;
    }

    public String getHomePage() {
        return homePage;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(aboutYou);
        dest.writeString(homePage);
        dest.writeParcelable(profileImage, flags);
    }
}
