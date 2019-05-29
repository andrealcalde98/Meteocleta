package cat.copernic.meteocleta;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PerfilFragment extends Fragment {
    EditText etname, etsurname, etpassword;
    Spinner etRol;
    TextView etDate, rol;
    Button change;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_perfil, null);
    }
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            // Setup any handles to view objects here
            etname = view.findViewById(R.id.etName);
            etsurname = view.findViewById(R.id.etSurname);
            etpassword = view.findViewById(R.id.etEnterPassword);
            etRol = view.findViewById(R.id.etRol);
            rol = view.findViewById(R.id.tv_rol);
            etDate = view.findViewById(R.id.etDate);
            change = view.findViewById(R.id.btn_change);
            dialog = new ProgressDialog(getActivity());

            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference( "usuarios/" +  mAuth.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //dataSnapshot viene de leer "usuarios/" + user.getUid() + "/comidas"
                    for (DataSnapshot datos : dataSnapshot.getChildren()) {
                        String birthday = datos.child("birthday").getValue(String.class);
                        String name = datos.child("name").getValue(String.class);
                        String role = datos.child("role").getValue(String.class);
                        String surname = datos.child("surname").getValue(String.class);

                        etname.setText(name);
                        etsurname.setText(surname);
                        etDate.setText(birthday);
                        rol.setText(role);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.w("perfil", "Failed to read value.", databaseError.toException());
                    Toast.makeText(getActivity(), "Error al leer", Toast.LENGTH_SHORT);

                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etname.getText().toString().isEmpty() || etsurname.getText().toString().isEmpty()
                            || etDate.getText().toString().isEmpty()
                            || etRol.getSelectedItem().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Missing fields",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        añadirDatos();
                        updatePassword();
                    }
                }
            });

            ArrayList<String> roles = new ArrayList<>();
            roles.add("Student");
            roles.add("Employee");
            etRol.setPrompt("Choose your role");
            ArrayAdapter adp = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, roles);
            etRol.setAdapter(adp);


            etRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String rol = (String) etRol.getAdapter().getItem(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            dateSetListener,
                            year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            });
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;

                    Log.d("fecha", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                    String date = dayOfMonth + "/" + month + "/" + year;
                    etDate.setText(date);
                }
            };
        }


    private void añadirDatos() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios/" + mAuth.getUid() + "/datos/");

        myRef.child("name").setValue(etname.getText().toString());
        myRef.child("surname").setValue(etsurname.getText().toString());
        myRef.child("role").setValue(etRol.getSelectedItem().toString());
        myRef.child("birthday").setValue(etDate.getText().toString());
        //Toast.makeText(getActivity(), "Update successful", Toast.LENGTH_SHORT).show();
    }

    private void updatePassword(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            dialog.setMessage("Updating, please wait...");
            dialog.show();
            user.updatePassword(etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),"updated", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(getActivity(), MainActivity.class);
                        //startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(),"Error password not updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}




