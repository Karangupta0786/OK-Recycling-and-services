package com.okjunkstore.beta;

import static com.okjunkstore.beta.api.RetrofitClient.retrofit1;
import static com.okjunkstore.beta.model.Constants.TOPIC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.okjunkstore.beta.Helperclass.HomeAdapter.ownerOrderData;
import com.okjunkstore.beta.Helperclass.HomeAdapter.SlotAdapter;
import com.okjunkstore.beta.Helperclass.HomeAdapter.slotHelperClass;
import com.okjunkstore.beta.NavigationDrawer.TermsAndConditions;
import com.okjunkstore.beta.api.ApiInterface;
import com.okjunkstore.beta.dashboard.DashboardActivity;
import com.okjunkstore.beta.model.FcmHttpV1Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
//// ////Schedule Timing
    TextView dateSelect, date;
    DatePickerDialog.OnDateSetListener setListener;
    RecyclerView recyclerView;
    SlotAdapter slotAdapter;
//// ////Schedule Timing

//// ////Weight radio btn
    RadioGroup radioGroup;
    TextView atLeast;
//// ////Weight radio btn


/// //// // //// //Get Location->
    Button btn;
    FusedLocationProviderClient fusedLocationProviderClient;
/// //// // //// //Get Location->

/////  ///  //items
    Chip plastic,newspaper,iron,steel,bike,car,tv,fridge,oven,ac,fan,cooler,washing,electrics,alloy;
    ArrayList<String> selectedChipData;
/////  ///  //items

    ImageView viewI;
    FloatingActionButton addImageBtn;
    Spinner locality;
    String categories;
    TextInputLayout say, ownerName, phone, address;
    TextView updateBtn;
    Bitmap bitmap;

    String downloadUrl = "";


    CheckBox agree;
    ImageView terms;
    Dialog dialog;

    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference, dbRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order);

//// ////Weight radio btn
        atLeast = findViewById(R.id.atleast);
        radioGroup = findViewById(R.id.radio_grp);
        RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textView's text to "Checked: example radiobutton text"
                    atLeast.setText("I have at least : " + checkedRadioButton.getText() + " material");
                }
            }
        });
//// ////Weight radio btn

        recyclerView = findViewById(R.id.recycler_time_slot);

        ArrayList<slotHelperClass> slotTiming = new ArrayList<>();
        slotTiming.add(new slotHelperClass("09:00 AM - 12:00PM"));
        slotTiming.add(new slotHelperClass("12:00 PM - 03:00PM"));
        slotTiming.add(new slotHelperClass("03:00 PM - 05:00PM"));

        slotAdapter = new SlotAdapter(slotTiming);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(slotAdapter);

        date = findViewById(R.id.date);
        dateSelect = findViewById(R.id.date_select);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateSelect.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    OrderActivity.this, (datePicker, year1, month1, day1) -> {
                        month1 = month1 + 1;
                        String date = day1 + "/" + month1 + "/" + year1;
                        dateSelect.setText(date);
                    },year,month,day);
            datePickerDialog.show();
        });

        dialog = new Dialog(OrderActivity.this);
        progressDialog = new ProgressDialog(OrderActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notice");
        storageReference = FirebaseStorage.getInstance().getReference();


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //Get Location ->
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        address = findViewById(R.id.address);
        btn = findViewById(R.id.btn);

        //    Chip Items
        plastic = findViewById(R.id.plasticsChip);
        iron = findViewById(R.id.ironChip);
        steel = findViewById(R.id.steelChip);
        newspaper = findViewById(R.id.booksChip);
        bike = findViewById(R.id.bikeChip);
        car = findViewById(R.id.carChip);
        tv = findViewById(R.id.tvChip);
        fridge = findViewById(R.id.fridgeChip);
        oven = findViewById(R.id.ovenChip);
        ac = findViewById(R.id.acChip);
        fan = findViewById(R.id.fanChip);
        cooler = findViewById(R.id.coolerChip);
        washing = findViewById(R.id.washingChip);
        electrics = findViewById(R.id.electronicChip);
        alloy = findViewById(R.id.alloysChip);

        selectedChipData = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener checkedChangeListener = (compoundButton, isChecked) -> {
            if (isChecked){
                selectedChipData.add(compoundButton.getText().toString());
            } else{
                selectedChipData.remove(compoundButton.getText().toString());
            }
        };
        plastic.setOnCheckedChangeListener(checkedChangeListener);
        iron.setOnCheckedChangeListener(checkedChangeListener);
        steel.setOnCheckedChangeListener(checkedChangeListener);
        newspaper.setOnCheckedChangeListener(checkedChangeListener);
        alloy.setOnCheckedChangeListener(checkedChangeListener);
        bike.setOnCheckedChangeListener(checkedChangeListener);
        car.setOnCheckedChangeListener(checkedChangeListener);
        tv.setOnCheckedChangeListener(checkedChangeListener);
        fridge.setOnCheckedChangeListener(checkedChangeListener);
        oven.setOnCheckedChangeListener(checkedChangeListener);
        ac.setOnCheckedChangeListener(checkedChangeListener);
        fan.setOnCheckedChangeListener(checkedChangeListener);
        cooler.setOnCheckedChangeListener(checkedChangeListener);
        washing.setOnCheckedChangeListener(checkedChangeListener);
        electrics.setOnCheckedChangeListener(checkedChangeListener);

        agree = findViewById(R.id.agree);
        terms = findViewById(R.id.terms);

        viewI = findViewById(R.id.upload_image_view);
        locality = findViewById(R.id.image_dropdown);
        say = findViewById(R.id.saySomthing);
        ownerName = findViewById(R.id.owner);
        phone = findViewById(R.id.phoneNo);
        addImageBtn = findViewById(R.id.addImage);
        updateBtn = findViewById(R.id.done);

        String[] items = new String[]{"House","Apartment","Shop","School"};
        locality.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,items));

        locality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categories = locality.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addImageBtn.setOnClickListener(view -> openGallery());

        terms.setOnClickListener(view -> startActivity(new Intent(OrderActivity.this, TermsAndConditions.class)));

        updateBtn.setOnClickListener(view -> {
            if (!validateOwner() | !validatePhone() | !validateAddress()) {
                return;
            }if (categories.equals("Select Locality")){
                Toast.makeText(OrderActivity.this, "Please Select Locality", Toast.LENGTH_SHORT).show();
            }if (!agree.isChecked()){
                Toast.makeText(OrderActivity.this, "Please check the box", Toast.LENGTH_SHORT).show();
            }
            else if (bitmap == null){
                uploadData();
            }else {
                uploadImage();
            }
            String name = ownerName.getEditText().getText().toString();
            String titleTxt = "Thank You "+name;
            String bodyTxt = "We Received an Order from : " + categories;
            sendNotify(titleTxt, bodyTxt);
        });
//       addImageBtn.setOnClickListener(new View.OnClickListener() {
////          @Override
//           public void onClick(View view) {
//               ImagePicker.Companion.with(UserDashboard.this)
//                       .crop()	    			//Crop image(Optional), Check Customization for more option
//                       .compress(1024)
//                       .maxResultSize(1080,1080)
//                       .start();
//                }
//         });

        btn.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(OrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else
                ActivityCompat.requestPermissions(OrderActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        });
    }

    private void showLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location!= null){
                Geocoder geocoder = new Geocoder(OrderActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    address.getEditText().setText(addressList.get(0).getAddressLine(0));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(OrderActivity.this, "Location Null Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotify(String title, String body){
        ApiInterface apiService = retrofit1.create(ApiInterface.class);

// Prepare the message
        FcmHttpV1Request.Notification notification = new FcmHttpV1Request.Notification();
        notification.title = title;
        notification.body = body;

        FcmHttpV1Request.Message message = new FcmHttpV1Request.Message();
        message.topic = "news";
        message.notification = notification;
        message.data = new HashMap<>();
        message.data.put("story_id", "story_12345");


        FcmHttpV1Request request = new FcmHttpV1Request(message);
        Gson gson = new Gson();
        Log.d("FCM-Payload", gson.toJson(request));

// Call FCM API
        apiService.sendNotificationNew(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("FCM", "Notification Sent Successfully");
                    Toast.makeText(OrderActivity.this, "Notification Sent Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderActivity.this, "Notification Error", Toast.LENGTH_SHORT).show();
                    Log.e("FCM", "Error Code: " + response.code());
                    Log.e("FCM", "Error Body: " + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Notification Error", Toast.LENGTH_SHORT).show();
                Log.e("FCM", "Failed: " + t.getMessage());
            }
        });
    }

     private void uploadImage() {
         progressDialog.setMessage("Please wait, Uploading...");
         progressDialog.setCanceledOnTouchOutside(false);
         progressDialog.show();

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
         byte[] finalImg = baos.toByteArray();
         final StorageReference filePath;
         filePath = storageReference.child("Notice").child(finalImg+"jpg");
         final UploadTask uploadTask = filePath.putBytes(finalImg);
         uploadTask.addOnCompleteListener(OrderActivity.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                 if (task.isSuccessful()){
                     uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     downloadUrl = String.valueOf(uri);
                                     uploadData();
                                 }
                             });
                         }
                     });
                 }else {
                     progressDialog.dismiss();
                     Toast.makeText(OrderActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }

     private void uploadData() {
         progressDialog.setTitle("Please wait..");
         progressDialog.setCanceledOnTouchOutside(false);
         progressDialog.show();

         dbRef = databaseReference.child(categories);
         final String uniqueKey = dbRef.push().getKey();

        String title = say.getEditText().getText().toString();
        String owner = ownerName.getEditText().getText().toString();
        String contact = phone.getEditText().getText().toString();
        String Add = address.getEditText().getText().toString();

         String tAndC = agree.getText().toString();
         String scheduleWeight = atLeast.getText().toString();
         String junkItems = selectedChipData.toString();
         String scheduleDate = dateSelect.getText().toString();
         String item = scheduleWeight + junkItems + "[Schedule DATE - " + scheduleDate + "]";

         Calendar calForDate = Calendar.getInstance();
         SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
         String date = currentDate.format(calForDate.getTime());

         Calendar calForTime = Calendar.getInstance();
         SimpleDateFormat currentTime = new SimpleDateFormat("hh-mm a");
         String time = currentTime.format(calForTime.getTime());

         ownerOrderData noticeData = new ownerOrderData(owner,contact,title,downloadUrl,date,time,uniqueKey,item, tAndC,Add);
         dbRef.child(owner).setValue(noticeData).addOnSuccessListener(unused -> {
             progressDialog.dismiss();
             Toast.makeText(OrderActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
             openOrderDialog();
         }).addOnFailureListener(e -> Toast.makeText(OrderActivity.this, "Failed", Toast.LENGTH_SHORT).show());
     }

    private void openOrderDialog() {
        dialog.setContentView(R.layout.order_dialoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btn = dialog.findViewById(R.id.our);
        TextView close = dialog.findViewById(R.id.close);

        close.setOnClickListener(view -> {
            dialog.dismiss();
            startActivity(new Intent(OrderActivity.this, DashboardActivity.class));
            finish();
        });
        btn.setOnClickListener(view -> {
            dialog.dismiss();
            startActivity(new Intent(OrderActivity.this,UserProfile.class));
            finish();
        });
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void openGallery() {
         Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        int REQ = 1;
        startActivityForResult(pickImage, REQ);
     }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
             try {
                 Uri uri = data.getData();
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
             } catch (IOException e) {
                 Log.e("ImagePicking Error", "OnActivityResult : " + e);
             }
             viewI.setImageBitmap(bitmap);
         }

     private boolean validateOwner() {
         String val = ownerName.getEditText().getText().toString();
         if (val.isEmpty()) {
             ownerName.setError("Field can not be empty");
             return false;
         } else {
             ownerName.setError(null);
             ownerName.setErrorEnabled(false);
             return true;
         }
     }

     private boolean validateAddress() {
         String val = address.getEditText().getText().toString();
         if (val.isEmpty()) {
             address.setError("Field can not be empty");
             return false;
         } else {
             address.setError(null);
             address.setErrorEnabled(false);
             return true;
         }
     }

     private boolean validatePhone() {
         String val = phone.getEditText().getText().toString();
         if (val.isEmpty()) {
             phone.setError("Field can not be empty");
             return false;
         }else if (val.length() != 10) {
             phone.setError("Invalid Phone");
             return false;
         }else {
             phone.setError(null);
             phone.setErrorEnabled(false);
             return true;
         }
     }
 }