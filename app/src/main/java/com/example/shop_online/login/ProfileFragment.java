package com.example.shop_online.login;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_online.R;
import com.example.shop_online.admin.ManagementActivity;
import com.example.shop_online.order.OrdersActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private Button logoutButton, ordersButton, adminManagementButton;
    private TextView userNameText;
    private String userName;
    private ImageView imageProfile;
    private String userId, path, picturePath;
    private Bitmap bitmap;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storage = FirebaseStorage.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        // single path for every user
        // modify rules from firebase storage, just user with .auth.uid == uid can acces the correspondending image
        path = "users/" + userId + "/profile.jpg";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = v.findViewById(R.id.logoutButton);
        ordersButton = v.findViewById(R.id.ordersButton);
        adminManagementButton = v.findViewById(R.id.managementAdminButton);
        userNameText = v.findViewById(R.id.TextUserName);
        logoutButton.setOnClickListener(this);
        ordersButton.setOnClickListener(this);
        adminManagementButton.setOnClickListener(this);
        imageProfile = v.findViewById(R.id.profileImage);
        imageProfile.setClickable(true);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        // take details from user
        // user name = last name + first name
        // setup buttons for com.example.shop_online.admin user or simple user
        getDataFromUser();
        // check if user has profile image, and set image
        checkIfUserHasProfileImage();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logoutButton:
                logOut();
                break;
            case R.id.ordersButton:
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intent);
                break;
            case R.id.managementAdminButton:
                Intent intent1 = new Intent(getActivity(), ManagementActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void getDataFromUser(){
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               userName = snapshot.child("lastName").getValue().toString() + " " + snapshot.child("firstName").getValue().toString();
               userNameText.setText(userName);
               Boolean admin = (Boolean) snapshot.child("admin").getValue();
               if(admin.booleanValue()){
                    ordersButton.setVisibility(View.GONE);
                    adminManagementButton.setVisibility(View.VISIBLE);
               }
               else{
                    adminManagementButton.setVisibility(View.GONE);
                    ordersButton.setVisibility(View.VISIBLE);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void checkIfUserHasProfileImage(){
        storage.getReference(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // image exist
                Picasso.get().load(uri).fit().into(imageProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // image not found
                Log.i("Database","Profil Image not found");
            }
        });
    }


    public void uploadImage(){
        // Intent for acces media gallery
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage =  data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContext().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        bitmap = adjustImage(picturePath);
                        imageProfile.setImageBitmap(bitmap);

                        // upload image to firebase storage
                        uploadImageToFirebaseStorage();
                        cursor.close();
                    }
                }

            }

        }

    }


    private void uploadImageToFirebaseStorage() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] dataImage = stream.toByteArray();
            // upload image on specific path for user
            StorageReference storageReference = storage.getReference(path);
            UploadTask uploadTask = storageReference.putBytes(dataImage);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Your image succesfully uploaded!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public Bitmap adjustImage(String picturePath){
        Bitmap b = null;
        int reqHeight = 135, reqWidth = 135;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        b = BitmapFactory.decodeFile(picturePath, options);

        // scale image with imageView width and height
        b = Bitmap.createScaledBitmap(b, reqWidth, reqHeight, true);

        return b;

    }


    public void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}