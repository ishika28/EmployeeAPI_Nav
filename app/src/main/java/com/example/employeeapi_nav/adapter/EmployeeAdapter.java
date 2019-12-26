package com.example.employeeapi_nav.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.employeeapi_nav.R;
import com.example.employeeapi_nav.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList=employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employeerecycler,parent,false);

        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {

        Employee employee= employeeList.get(position);
        holder.tvId.setText(Integer.toString(employee.getId()));
        holder.tvName.setText(employee.getEmployee_name());
        holder.tvAge.setText(Integer.toString(employee.getEmployee_age()));
        holder.tvSalary.setText(Float.toString(employee.getEmployee_salary()));
        holder.tvProfileImg.setText(employee.getProfile_image());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvName, tvAge, tvSalary, tvProfileImg;


        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId=itemView.findViewById(R.id.tvId);
            tvName=itemView.findViewById(R.id.tvName);
            tvAge=itemView.findViewById(R.id.tvAge);
            tvSalary=itemView.findViewById(R.id.tvSalary);
            tvProfileImg=itemView.findViewById(R.id.tvProfileImg);

        }
    }
}
