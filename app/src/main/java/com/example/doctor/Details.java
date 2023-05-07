package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doctor.DoctorHomePagePack.DoctorFrontPage;
import com.example.doctor.PatientHomePagePack.PatientHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Details extends AppCompatActivity {

    //    // creating variables for our edittext, button and dbhandler
    TextInputEditText R_NameEdt, R_PasswordEdt, R_EmailEdt, R_DobEdt,R_PhoneEdt,R_Confirm_pass,R_Weight,R_Exp,R_Build,R_Street,R_City,R_Country,R_From,R_To,R_Pincode,R_Fees;
    TextInputLayout Experience,Specialization,Blood_type,Weight,Fees,Building,Street,City,Pincode,Country,From,To,Gender;
    Button Register;
    TextView Address;



    String[] items={"Doctor","Patient"};
    String[] gt={"Female","Male","Other"};
    String[] Blood={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    String[] special={"Dermatologist","Orthopedic","Cardiologist","Psychologist","Neurologist","Dentist","Ophthalmologist"};

    String[] from={"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","20:00","21:30"};

    String[] to={"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","20:00","21:00"};

    AutoCompleteTextView autoCompleteTxt,autospecial,autoBlood,auto_Gender,autoFrom,autoTo;
    String autoconfirm;
    ArrayAdapter<String> adapterItems,adapterspecial,adapterBlood,adaptergender,adapterFrom,adapterTo;
    // Firebase Authentication
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;
    // Firebase Connection
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    private CollectionReference collectionReference=db.collection("Users");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        // initializing all our variables.
        R_NameEdt = findViewById(R.id.nameid);
        R_PasswordEdt = findViewById(R.id.password);
        R_Confirm_pass=findViewById(R.id.confirm_pass);
//       R_Gender=findViewById(R.id.Edi_gender);
        R_EmailEdt = findViewById(R.id.email);
      R_DobEdt = findViewById(R.id.dob);
       R_PhoneEdt = findViewById(R.id.phone);
       Gender=findViewById(R.id.gender);
        autoCompleteTxt=findViewById(R.id.auto_complete);
        auto_Gender=findViewById(R.id.autogender);
        autospecial=findViewById(R.id.special);
        autoBlood=findViewById(R.id.blood);
        Register = findViewById(R.id.btn_register);
        Experience=findViewById(R.id.exp);
        Pincode=findViewById(R.id.pincode);
        Street=findViewById(R.id.street);
        City=findViewById(R.id.city);
        Country=findViewById(R.id.country);
        Weight=findViewById(R.id.weight);
        Fees=findViewById(R.id.fee);
        Blood_type=findViewById(R.id.bloodgroup);
        Building=findViewById(R.id.building);
        Specialization=findViewById(R.id.invite);
        R_Weight=findViewById(R.id.Edi_weight);
        R_Street=findViewById(R.id.Edi_street);
        R_Build=findViewById(R.id.Edi_build);
        R_Exp=findViewById(R.id.Edi_exp);
        R_City=findViewById(R.id.Edi_city);
        R_Country=findViewById(R.id.Edi_country);
        R_Pincode=findViewById(R.id.Edi_pincode);
        R_Fees=findViewById(R.id.Edi_fees);
        From=findViewById(R.id.layoutFrom);
        To=findViewById(R.id.LayoutTo);
        autoFrom=findViewById(R.id.autoFrom);
        autoTo=findViewById(R.id.autoTo);

        R_DobEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(Details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our edit text.
                        R_DobEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                },

                        year, month, day);
                datePickerDialog.show();
            }
        });

        adapterItems=new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        adaptergender=new ArrayAdapter<String>(this,R.layout.list_item,gt);
        auto_Gender.setAdapter(adaptergender);
//
        adapterspecial=new ArrayAdapter<String>(this,R.layout.list_item,special);
        autospecial.setAdapter(adapterspecial);

        adapterBlood=new ArrayAdapter<String>(this,R.layout.list_item,Blood);
        autoBlood.setAdapter(adapterBlood);


        adapterFrom=new ArrayAdapter<String>(this,R.layout.list_item,from);
        autoFrom.setAdapter(adapterFrom);

        adapterTo=new ArrayAdapter<String>(this,R.layout.list_item,to);
        autoTo.setAdapter(adapterTo);


        auto_Gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String g=adapterView.getItemAtPosition(i).toString();
                Log.d("Tag","Clinic Time"+g);
            }
        });

        autospecial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String specials=adapterView.getItemAtPosition(i).toString();
                Log.d("Tag","Clinic Time"+specials);
            }
        });


        autoBlood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String bloods=adapterView.getItemAtPosition(i).toString();
                Log.d("Tag","Clinic Time"+bloods);
            }
        });

        autoFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String g=adapterView.getItemAtPosition(i).toString();
                Log.d("Tag","Clinic Time"+from);
            }
        });

        autoTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String g=adapterView.getItemAtPosition(i).toString();
                Log.d("Tag","Clinic Time"+to);
            }
        });

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Items "+item, Toast.LENGTH_SHORT).show();


                if(item=="Doctor") {
                    Specialization.setVisibility(View.VISIBLE);
                Experience.setVisibility(View.VISIBLE);
//                Address.setVisibility(View.VISIBLE);
                    Building.setVisibility(View.VISIBLE);
                    Street.setVisibility(View.VISIBLE);
                    City.setVisibility(View.VISIBLE);
                   Country.setVisibility(View.VISIBLE);
                    Pincode.setVisibility(View.VISIBLE);
                    Blood_type.setVisibility(View.GONE);
                    Weight.setVisibility(View.GONE);
                    Fees.setVisibility(View.VISIBLE);
                    From.setVisibility(View.VISIBLE);
                    To.setVisibility(View.VISIBLE);
//
                }
                if(item=="Patient"){
                    Blood_type.setVisibility(View.VISIBLE);
                    Fees.setVisibility(View.GONE);
                    Weight.setVisibility(View.VISIBLE);
                    Specialization.setVisibility(View.GONE);
                    Experience.setVisibility(View.GONE);
//                Address.setVisibility(View.VISIBLE);
                    Building.setVisibility(View.GONE);
                    Street.setVisibility(View.GONE);
                    City.setVisibility(View.GONE);
                    Country.setVisibility(View.GONE);
                    Pincode.setVisibility(View.GONE);
                    From.setVisibility(View.GONE);
                    To.setVisibility(View.GONE);
                }
//


            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(R_EmailEdt.getText().toString()) && !TextUtils.isEmpty(R_PasswordEdt.getText().toString())){
                    String email=R_EmailEdt.getText().toString();
                    String pass=R_PasswordEdt.getText().toString();

                    registerUser(email, pass);
                }
                else{
                    Toast.makeText(Details.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }




          }
        });
    }



    public void registerUser(String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, add data to firestore
                            Log.d("MYTAGS", "account created " +firebaseAuth.getUid());
                            addDataToFireStore();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("MYTAGS", "Could not create account "+task.getException());

                        }
                    }
                });
    }

    public void updateUserName(Users u)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(u.getName()).build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("MYTAGS", "User name updated");


                            if(u.getRole().equals("Doctor")){
                                Log.d("Register Doctor", "I am here");
                                Intent i = new Intent(Details.this, DoctorFrontPage.class);
                                startActivity(i);
                                finish();
                            }
                            else{

                                Intent i = new Intent(Details.this, PatientHomePage.class);
                                startActivity(i);
                                finish();
                            }

                        }
                        else{
                            Log.d("MYTAGS", "Could not update user name");
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDataToFireStore() {

        currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            final String currentUserId= currentUser.getUid();
            Users user=new Users();
            user.setUserId(currentUserId);
            user.setName(R_NameEdt.getText().toString().trim());
            user.setEmail(R_EmailEdt.getText().toString().trim());
            user.setPass(R_PasswordEdt.getText().toString().trim());
            user.setRepass(R_Confirm_pass.getText().toString().trim());
            user.setDob(R_DobEdt.getText().toString().trim());
        user.setGender(auto_Gender.getText().toString().trim());
        user.setPhone(R_PhoneEdt.getText().toString().trim());
        user.setRole(autoCompleteTxt.getText().toString().trim());
        if(user.getRole().equals("Doctor")){
            user.setSpecialisation(autospecial.getText().toString().trim());
            user.setExperience(R_Exp.getText().toString().trim());
            user.setFees(R_Fees.getText().toString().trim());
            user.setBuildingNo(R_Build.getText().toString().trim());
            user.setStreet(R_Street.getText().toString().trim());
            user.setCity(R_City.getText().toString().trim());
            user.setCountry(R_Country.getText().toString().trim());
            user.setPinCode(R_Pincode.getText().toString().trim());
            user.setTimeFrom(R_From.getText().toString().trim());
            user.setTimeTo(R_To.getText().toString().trim());
            setStartAndEndTime(user.getTimeFrom(), user.getTimeTo(),user.getUserId());
            Log.d("MyTags", "getting doctor data");
        }
        else{
            user.setBloodType(autoBlood.getText().toString().trim());
            user.setWeight(R_Weight.getText().toString().trim());
        }

            // below method is use to add data to Firebase FireStore users.

            db.collection("Users").document(currentUserId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("MYTAGS", "User added to firestore");
                    updateUserName(user);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MYTAGS", "Could not add user to firestore");
                }
            });


        }else{

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setStartAndEndTime(String t_start, String t_end,String DoctorId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime endTime = LocalTime.parse(t_end, formatter); // 8:00 AM
        LocalTime startTime = LocalTime.parse(t_start, formatter); // 5:00 PM
        // Define the time interval in minutes
        int intervalMinutes = 30;

        // Generate a list of times with the given interval
        List<LocalTime> timeList = generateTimeListWithInterval(startTime, endTime, intervalMinutes);



        LocalDate currentDate = LocalDate.now();

        // Create a list to store the dates
        List<LocalDate> nextThreeDays = new ArrayList<>();

        // Add the current date to the list
        nextThreeDays.add(currentDate);

        // Loop to add the next three days to the list
        for (int i = 1; i <= 3; i++) {
            LocalDate nextDay = currentDate.plus(i, ChronoUnit.DAYS);
            nextThreeDays.add(nextDay);
        }
        for(int i=0;i<nextThreeDays.size();i++) {
            for (int j = 0; j < timeList.size(); j++) {
                SlotModel s=new SlotModel();
                s.setTime(timeList.get(j).toString());
               s.setNumberOfPatients(0);
                db.collection("DoctorSlots").document(DoctorId)
                        .collection("Dates").document("" + nextThreeDays.get(i))
                        .collection("Slots").document("" +timeList.get(j))
                        .set(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Details", " created doctor slot");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Details", "Could not create doctor slot");
                            }
                        });
            }
        }

    }
    public static List<LocalTime> generateTimeListWithInterval(LocalTime start, LocalTime end, int intervalMinutes) {
        // Create a list to store the generated times
        List<LocalTime> timeList = new ArrayList<>();

        // Loop through the time range with the given interval and add each time to the list
        LocalTime currentTime = start;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (currentTime.isBefore(end) || currentTime.equals(end)) {
                timeList.add(currentTime);
                currentTime = currentTime.plusMinutes(intervalMinutes); // Increment by the given interval
            }
        }

        return timeList;
    }
}





