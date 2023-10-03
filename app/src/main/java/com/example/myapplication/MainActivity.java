package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

   MyDatabase myDatabase;
   private EditText Name,Age,Gender,uniqueId;
   private Button submit,Show,update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase=new MyDatabase(this);
        myDatabase.getWritableDatabase();
        Name=findViewById(R.id.EditName);
        Age=findViewById(R.id.EditAge);
        Gender=findViewById(R.id.EditGender);
        submit=findViewById(R.id.saveButton);
        Show=findViewById(R.id.ShowButton);
        update=findViewById(R.id.UpdateButton);
        uniqueId=findViewById(R.id.UniqueId);
        delete=findViewById(R.id.DeleteButton);

        submit.setOnClickListener(this);
        Show.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        String nameData=Name.getText().toString();
        String AgeData=Age.getText().toString();
        String GenderData=Gender.getText().toString();
        String ID=uniqueId.getText().toString();
        if(v.getId()==R.id.saveButton){
            long rowId=myDatabase.InsertData(nameData,AgeData,GenderData);
            if(rowId==-1){
                Toast.makeText(getApplicationContext(),"data save is not successfully",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext()," row "+rowId+" data save is successfully",Toast.LENGTH_LONG).show();
                Name.setText("");
                Age.setText("");
                Gender.setText("");
            }
        }
        if (v.getId()==R.id.ShowButton) {
            Cursor cursor=myDatabase.displayAllData();
            if(cursor.getCount()==0){
              //message is show here
                ShowData("Error", "no data found");
                return;
            }
            StringBuffer stringBuffer=new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                stringBuffer.append("Age : "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender : "+cursor.getString(3)+"\n");
            }
            ShowData("ResultSet",stringBuffer.toString());
        }
        if(v.getId()==R.id.UpdateButton){
           boolean IsUpdate= myDatabase.updateData(ID,nameData,AgeData,GenderData);
           if(IsUpdate==true){
               Toast.makeText(getApplicationContext(),"data update is  successfully",Toast.LENGTH_LONG).show();
           }
           else {
               Toast.makeText(getApplicationContext(),"data update is not successfully",Toast.LENGTH_LONG).show();
           }
        }
        if(v.getId()==R.id.DeleteButton){
            int count=myDatabase.deleteData(ID);
            if(count>0){
                Toast.makeText(getApplicationContext(),"data delete is  successfully",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"data delete is not successfully",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void  ShowData(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}