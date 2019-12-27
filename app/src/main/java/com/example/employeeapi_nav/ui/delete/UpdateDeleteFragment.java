package com.example.employeeapi_nav.ui.delete;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.employeeapi_nav.R;
import com.example.employeeapi_nav.api.EmployeeAPI;
import com.example.employeeapi_nav.model.Employee;
import com.example.employeeapi_nav.model.EmployeeCUD;
import com.example.employeeapi_nav.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDeleteFragment extends Fragment {

    private UpdateDeleteViewModel UpdateDeleteViewModel;
    private Button btnserachemp, btnupdate;
    private Button btndelete;
    private EditText etemployeeNumber;
    private EditText etempname, etempsalary, etempage;
    Retrofit retrofit;
    EmployeeAPI employeeAPI;
    AlertDialog.Builder builder;

    public static UpdateDeleteFragment newInstance() {
        return new UpdateDeleteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        UpdateDeleteViewModel = ViewModelProviders.of(this).get(UpdateDeleteViewModel.class);
        final View root =inflater.inflate(R.layout.update_delete_fragment, container, false);

        btnserachemp=root.findViewById(R.id.btnsearchemp);
        btnupdate=root.findViewById(R.id.btnupdate);
        btndelete=root.findViewById(R.id.btndeleteemp);
        etempname=root.findViewById(R.id.etempname);
        etempage=root.findViewById(R.id.etempage);
        etempsalary=root.findViewById(R.id.etempsalary);
        etemployeeNumber=root.findViewById(R.id.etemployeeNumber);
        builder =new AlertDialog.Builder(getContext());

        btnserachemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);


                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete employee ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteEmployee();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();

            }
        });
    return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UpdateDeleteViewModel = ViewModelProviders.of(this).get(UpdateDeleteViewModel.class);
        // TODO: Use the ViewModel
    }




    private void createInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        employeeAPI= retrofit.create(EmployeeAPI.class);
    }
    private void loadData(){
        createInstance();
        Call<Employee> employeeCall = employeeAPI.getEmployeeByID(Integer.parseInt(etemployeeNumber.getText().toString()));
        employeeCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {

                etempname.setText(response.body().getEmployee_name());
                etempsalary.setText(Float.toString(response.body().getEmployee_salary()));
                etempage.setText(Integer.toString(response.body().getEmployee_age()));
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void updateEmployee(){
        createInstance();
        EmployeeCUD employee = new EmployeeCUD(
                etempname.getText().toString(),
                Float.parseFloat(etempsalary.getText().toString()),
                Integer.parseInt(etempage.getText().toString())
        );
        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etemployeeNumber.getText().toString()),employee);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEmployee(){
        createInstance();
        Call<Void> callvoid =employeeAPI.deleteEmployee(Integer.parseInt(etemployeeNumber.getText().toString()));

        callvoid.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
