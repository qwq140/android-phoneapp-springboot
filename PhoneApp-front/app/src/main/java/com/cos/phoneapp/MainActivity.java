package com.cos.phoneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    private PhoneAdapter phoneAdapter;
    private RecyclerView rvPhone;
    private PhoneService phoneService = PhoneService.retrofit.create(PhoneService.class);
    private FloatingActionButton fab;
    private List<Phone> phones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvPhone = findViewById(R.id.rv_phone);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(rvPhone.getContext(),new LinearLayoutManager(this).getOrientation());
        rvPhone.addItemDecoration(dividerItemDecoration);


        rvPhone.setLayoutManager(manager);



        findAll();

        fab = findViewById(R.id.fab_save);
        fab.setOnClickListener(v -> {
            addPhone();
        });

    }

    public void delete(Long id, Phone phone){
        Call<CommonRespDto<Void>> call = phoneService.delete(id);
        call.enqueue(new Callback<CommonRespDto<Void>>() {
            @Override
            public void onResponse(Call<CommonRespDto<Void>> call, Response<CommonRespDto<Void>> response) {
                Log.d(TAG, "onResponse: 삭제하기 성공");
                phones.remove(phone);
                phoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommonRespDto<Void>> call, Throwable t) {
                Log.d(TAG, "onFailure: 삭제하기 실패");
            }
        });
    }

    public void update(Long id, Phone phone){
        Call<CommonRespDto<Phone>> call = phoneService.update(id, phone);
        call.enqueue(new Callback<CommonRespDto<Phone>>() {
            @Override
            public void onResponse(Call<CommonRespDto<Phone>> call, Response<CommonRespDto<Phone>> response) {
                Log.d(TAG, "onResponse: 수정하기 성공");
                phoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommonRespDto<Phone>> call, Throwable t) {
                Log.d(TAG, "onFailure: 수정하기 실패");
            }
        });
    }

    public void editPhone(int position, Phone phone){
        View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dailog_item,null);

        final EditText etName = dialog.findViewById(R.id.et_name);
        final EditText etTel = dialog.findViewById(R.id.et_tel);

        etName.setText(phone.getName());
        etTel.setText(phone.getTel());
        Long id = phone.getId();

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 수정");
        dlg.setView(dialog);

        String name = etName.getText().toString();
        String tel = etTel.getText().toString();
        phone.setName(name);
        phone.setTel(tel);

        dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(id,phone);
            }
        });
        dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(id,phone);
            }
        });
        dlg.show();

    }


    public void createPhone(String name, String tel){
        Phone phone = new Phone();
        phone.setName(name);
        phone.setTel(tel);
        Call<CommonRespDto<Phone>> call = phoneService.save(phone);
        call.enqueue(new Callback<CommonRespDto<Phone>>() {
            @Override
            public void onResponse(Call<CommonRespDto<Phone>> call, Response<CommonRespDto<Phone>> response) {
                CommonRespDto<Phone> commonRespDto = response.body();
                Phone phone = commonRespDto.getData();
                phones.add(phone);
                phoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommonRespDto<Phone>> call, Throwable t) {

            }
        });
    }

    public void addPhone(){
        View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dailog_item,null);

        final EditText etName  = dialog.findViewById(R.id.et_name);
        final EditText etTel = dialog.findViewById(R.id.et_tel);

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 등록");
        dlg.setView(dialog);
        dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createPhone(etName.getText().toString(),etTel.getText().toString());
            }
        });
        dlg.setNegativeButton("닫기",null);
        dlg.show();
    }

    public void findAll(){
        Call<CommonRespDto<List<Phone>>> call = phoneService.findAll();

        call.enqueue(new Callback<CommonRespDto<List<Phone>>>() {
            @Override
            public void onResponse(Call<CommonRespDto<List<Phone>>> call, Response<CommonRespDto<List<Phone>>> response) {
                CommonRespDto<List<Phone>> commonRespDto = response.body();
                phones = commonRespDto.getData();
                // 어댑터에게 넘기기
                Log.d(TAG, "onResponse: 응답 받은 데이터 : "+phones.toString());
                phoneAdapter = new PhoneAdapter(phones,MainActivity.this);
                rvPhone.setAdapter(phoneAdapter);
            }

            @Override
            public void onFailure(Call<CommonRespDto<List<Phone>>> call, Throwable t) {
                Log.d(TAG, "onFailure: findAll() 실패");
            }
        });
    }
}