package com.project.tlustudents;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
private ImageView imgAvatar;
private TextView txtName;
private TextView txtSid;
    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        imgAvatar = itemView.findViewById(R.id.img_student_ava);
        txtName = itemView.findViewById(R.id.txt_student_name);
        txtSid = itemView.findViewById(R.id.txt_student_sid);
    }
    public void bind(Student std){
        imgAvatar.setImageResource(std.getAvatar());
        txtName.setText(std.getFullname());
        txtSid.setText(std.getSid());
    }
}
