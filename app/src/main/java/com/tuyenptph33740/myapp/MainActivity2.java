package com.tuyenptph33740.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    TextView tvKQ;
    FirebaseFirestore database;
    Context context = this;
    String strKQ="";
    ToDo toDo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvKQ = findViewById(R.id.tvKQ);
        database = FirebaseFirestore.getInstance();//Khoi tao
//        insert();
//        update();
//        select();
        delete();
    }
    void insert(){
       String id = UUID.randomUUID().toString();//lay chuoi ngau nhien
       toDo = new ToDo(id,"title 11","content 11");//tao doi tuong moi de insert
        database.collection("TODO")//Truy cap den bang du lieu
                .document(id)//Truy cap den dong du lieu
                .set(toDo.convertToHashMap())//Dua du lieu vao dong
                .addOnSuccessListener(new OnSuccessListener<Void>() {//thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "insert thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void update(){
        String id ="3b92f049-f1da-4d4c-9f55-01451e19ddfe";//Copy id vao day
        toDo = new ToDo(id,"title 11 update","content 11 update");//noi dung can update
        database.collection("TODO")//lay bang du lieu
                .document(id)//lay id
                .update(toDo.convertToHashMap())//thuc hien update
                .addOnSuccessListener(new OnSuccessListener<Void>() {//thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "update that bai", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    void delete(){
        String id="3b92f049-f1da-4d4c-9f55-01451e19ddfe";//copy id
        database.collection("TODO")//truy cap vao bang du lieu
                .document(id)//truy cap vao id
                .delete()//thuc hien xoa
                .addOnCompleteListener(new OnCompleteListener<Void>() {//Xoa thanh cong
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")//truy cap vao bang du lieu
                .get()//Lay ve du lieu
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            strKQ="";
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                ToDo t=doc.toObject(ToDo.class);//chuyen du lieu doc duoc sang ToDo
                                list.add(t);
                                strKQ+= "id: "+t.getId()+"\n";
                                strKQ+= "title: "+t.getTitle()+"\n";
                                strKQ+= "content: "+t.getContent()+"\n";
                            }
                            tvKQ.setText(strKQ);
                        }
                        else {
                            Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
           return list;
    }
}