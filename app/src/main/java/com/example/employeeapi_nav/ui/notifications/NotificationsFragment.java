package com.example.employeeapi_nav.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.employeeapi_nav.R;
import com.example.employeeapi_nav.api.EmployeeAPI;
import com.example.employeeapi_nav.model.Employee;
import com.example.employeeapi_nav.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private EditText etemployeeNo;
    private TextView tvData;
    private Button btnsearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        etemployeeNo=root.findViewById(R.id.etemployeeNo);
        tvData=root.findViewById(R.id.tvData);
        btnsearch=root.findViewById(R.id.btnsearch);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loadData();

            }

        });

        return root;
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<Employee> listcall =employeeAPI.getEmployeeByID(Integer.parseInt(etemployeeNo.getText().toString()));

        listcall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Toast.makeText(getContext(),response.body().toString(), Toast.LENGTH_SHORT).show();
                String data ="";
                data +="Id :" + response.body().getId() +"\n";
                data +="Name :" + response.body().getEmployee_name() +"\n";
                data +="Age :" + response.body().getEmployee_age() +"\n";
                data +="Salary :" + response.body().getEmployee_salary() +"\n";

                tvData.setText(data);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}