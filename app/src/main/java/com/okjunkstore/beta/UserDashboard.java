package com.okjunkstore.beta;

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
import android.view.View;
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
import com.okjunkstore.beta.Helperclass.HomeAdapter.ownerOrderData;
import com.okjunkstore.beta.Helperclass.HomeAdapter.NotificationData;
//import com.okjunkstore.beta.Helperclass.HomeAdapter.SlotAdapter;
import com.okjunkstore.beta.Helperclass.HomeAdapter.SlotAdapter;
import com.okjunkstore.beta.Helperclass.HomeAdapter.slotHelperClass;
import com.okjunkstore.beta.NavigationDrawer.TermsAndConditions;
import com.okjunkstore.beta.api.ApiUtilities;
import com.okjunkstore.beta.model.PushNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashboard extends AppCompatActivity {
//// ////Schedule Timing
    TextView dateSelect, date;
    DatePickerDialog.OnDateSetListener setListener;
    RecyclerView recyclerView;
    SlotAdapter slotAdapter;
//// ////Schedule Timing

//// ////Weight radio btn
    RadioGroup radioGroup;
    TextView atleast;
//// ////Weight radio btn


/// //// // //// //Get Location->
    Button btn;
    FusedLocationProviderClient fusedLocationProviderClient;
/// //// // //// //Get Location->

/////  ///  //items
    Chip plastic,newspaper,iron,steel,bike,car,tv,fridge,oven,ac,fan,cooler,washing,electrics,alloy;
    ArrayList<String> selectedChipData;
/////  ///  //items

   /* MaterialCardView selectCard;
    TextView tvCourses;
    boolean[] selectedCourses;
    ArrayList<Integer> courseList = new ArrayList<>();
    String[] courseArray = {"Electronics", "Iron(LOHA)", "NewsPaper", "Plastic", "Steel", "Old MotorBike", "Old car", "Alloys(Copper,Bronze)"};
*/
    ImageView viewI;
    FloatingActionButton addImageBtn;
    Spinner locality;
    String categories;
    TextInputLayout say, ownerName, phone, address;
    TextView updateBtn;
    Bitmap bitmap;
    private final int REQ = 1;

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
//         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

//// ////Weight radio btn
        atleast = findViewById(R.id.atleast);
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
                    // Changes the textview's text to "Checked: example radiobutton text"
                    atleast.setText("I have at least :" + checkedRadioButton.getText() + " material");
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
       /* datetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UserDashboard.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
      setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                datetv.setText(date);
            }
        };*/
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UserDashboard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        dateSelect.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        dialog = new Dialog(UserDashboard.this);
        progressDialog = new ProgressDialog(UserDashboard.this);
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
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    selectedChipData.add(compoundButton.getText().toString());
                }
                else{
                    selectedChipData.remove(compoundButton.getText().toString());
                }
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

      /*  selectCard = findViewById(R.id.selectCard);
        tvCourses = findViewById(R.id.tvCourses);
        selectedCourses = new boolean[courseArray.length];
        selectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCoursesDialoge();
            }
        });*/

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
        locality.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

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

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this, TermsAndConditions.class));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateOwner() | !validatePhone() | !validateAddress()) {
                    return;
                }if (categories.equals("Select Locality")){
                    Toast.makeText(UserDashboard.this, "Please Select Locality", Toast.LENGTH_SHORT).show();
                }if (!agree.isChecked()){
                    Toast.makeText(UserDashboard.this, "Please check the box", Toast.LENGTH_SHORT).show();
                }
                else if (bitmap == null){
                    uploadData();
                }else {
                    uploadImage();
                }
                String name = ownerName.getEditText().getText().toString();
                String titletxt = "Thank You "+name;
                String msgtxt = "We Received Your Order from your : " + categories;
                PushNotification notification = new PushNotification(new NotificationData(titletxt,msgtxt),TOPIC);
                sendNotification(notification);
            }
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
            if (ActivityCompat.checkSelfPermission(UserDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else
                ActivityCompat.requestPermissions(UserDashboard.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
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
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location!= null){
                    Geocoder geocoder = new Geocoder(UserDashboard.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        address.getEditText().setText(addressList.get(0).getAddressLine(0));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(UserDashboard.this, "Location Null Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

     private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful())
                    Toast.makeText(UserDashboard.this, "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(UserDashboard.this, "error", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(UserDashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    // Select Items by dropdown
    private void showCoursesDialoge() {
         AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboard.this);
         builder.setTitle("Select Items");
         builder.setCancelable(false);

         builder.setMultiChoiceItems(courseArray, selectedCourses, new DialogInterface.OnMultiChoiceClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                 if (b){
                     courseList.add(i);
                     Collections.sort(courseList);
                 }else{
                     courseList.remove(i);
                 }
             }
         }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 StringBuilder stringBuilder = new StringBuilder();
                 for (int j=0; j < courseList.size(); j++){
                     stringBuilder.append(courseArray[courseList.get(j)]);

                     //check Condition
                     if (j != courseList.size() - 1){
                         stringBuilder.append(", ");
                     }
                     tvCourses.setText(stringBuilder.toString());
                 }
             }
         }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
             }
         }).setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 for (int j = 0; j < selectedCourses.length; j++){
                     selectedCourses[j] = false;
                     courseList.clear();
                     tvCourses.setText("");
                 }
             }
         });
         builder.show();
     }*/

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
         uploadTask.addOnCompleteListener(UserDashboard.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                     Toast.makeText(UserDashboard.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }

     private void uploadData() {
         progressDialog.setMessage("Please wait..");
         progressDialog.setCanceledOnTouchOutside(false);
         progressDialog.show();

         dbRef = databaseReference.child(categories);
         final String uniqueKey = dbRef.push().getKey();

        String title = say.getEditText().getText().toString();
        String owner = ownerName.getEditText().getText().toString();
        String contact = phone.getEditText().getText().toString();
        String Add = address.getEditText().getText().toString();

//        String item = tvCourses.getText().toString();
//        plastic,iron,steel,newspaper,bike,car,tv,fridge,oven,ac,fan,cooler,washing,electrics;

         String TandC = agree.getText().toString();
         String scheduleWeight = atleast.getText().toString();
         String junkItems = selectedChipData.toString();
         String scheduleDate = dateSelect.getText().toString();
         String item = scheduleWeight + junkItems + "[Schedule DATE - " + scheduleDate + "]";

         Calendar calForDate = Calendar.getInstance();
         SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
         String date = currentDate.format(calForDate.getTime());

         Calendar calForTime = Calendar.getInstance();
         SimpleDateFormat currentTime = new SimpleDateFormat("hh-mm a");
         String time = currentTime.format(calForTime.getTime());

         ownerOrderData noticeData = new ownerOrderData(owner,contact,title,downloadUrl,date,time,uniqueKey,item,TandC,Add);
         dbRef.child(owner).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 progressDialog.dismiss();
                 Toast.makeText(UserDashboard.this, "Uploaded", Toast.LENGTH_SHORT).show();
                 openOrderDialog();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(UserDashboard.this, "Failed", Toast.LENGTH_SHORT).show();
             }
         });
     }

    private void openOrderDialog() {
        dialog.setContentView(R.layout.order_dialoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btn = dialog.findViewById(R.id.our);
        TextView close = dialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(UserDashboard.this, RetailerDashboard.class));
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(UserDashboard.this,UserProfile.class));
                finish();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void openGallery() {
         Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         startActivityForResult(pickImage, REQ);
     }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
             Uri uri = data.getData();
             try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
             } catch (IOException e) {
                 e.printStackTrace();
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
         String val = (phone.getEditText().getText()).toString();
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