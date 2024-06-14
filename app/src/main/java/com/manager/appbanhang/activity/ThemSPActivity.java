package com.manager.appbanhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.manager.appbanhang.R;
import com.manager.appbanhang.databinding.ActivityThemSpactivityBinding;
import com.manager.appbanhang.model.MessageModel;
import com.manager.appbanhang.model.SanPhamMoi;
import com.manager.appbanhang.retrofit.ApiBanHang;
import com.manager.appbanhang.retrofit.RetrofitClient;
import com.manager.appbanhang.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSPActivity extends AppCompatActivity {
    Spinner themsp_loai;
    int loai = 0;
    Toolbar toolbar;
    ActivityThemSpactivityBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    SanPhamMoi sanPhamSua;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpactivityBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        initView();
        initData();
        ActionToolbar();
        Intent intent = getIntent();
        sanPhamSua = (SanPhamMoi) intent.getSerializableExtra("sua");
        if (sanPhamSua == null){
            //thêm
            flag = false;
        }else {
            //sửa
            flag = true;
            binding.toolbarthemsp.setTitle("Sửa sản phẩm");
            binding.btnthem.setText("Sửa sản phẩm");
            //hiện thông tin sản phẩm
            binding.themspTen.setText(sanPhamSua.getTensp());
            binding.themspGia.setText(sanPhamSua.getGia() + "");
            binding.themspSoluongkho.setText(sanPhamSua.getSoluongkho() + "");
            binding.themspLink.setText(sanPhamSua.getLinkyoutube() + "");
            binding.themspHinhanh.setText(sanPhamSua.getHinhanh());
            binding.themspMota.setText(sanPhamSua.getMota());
            binding.themspLoai.setSelection(sanPhamSua.getLoai());
        }

    }
    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Chọn loại sản phẩm");
        stringList.add("Loại 1");
        stringList.add("Loại 2");
        stringList.add("Loại 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringList);
        themsp_loai.setAdapter(adapter);
        themsp_loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false){
                    themsanpham();
                }else {
                    suasanpham();
                }
            }
        });
        binding.imguphinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    private void suasanpham() {
        String str_ten = binding.themspTen.getText().toString().trim();
        String str_gia = binding.themspGia.getText().toString().trim();
        String str_mota = binding.themspMota.getText().toString().trim();
        String str_hinhanh = binding.themspHinhanh.getText().toString().trim();
        String str_linkytb = binding.themspLink.getText().toString().trim();
        String str_slkho = binding.themspSoluongkho.getText().toString();
        if (TextUtils.isEmpty(str_linkytb) || TextUtils.isEmpty(str_slkho) || TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) | TextUtils.isEmpty(str_hinhanh) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui Lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
        }else{
            compositeDisposable.add(apiBanHang.suasp(str_ten,str_gia,str_hinhanh,str_mota,loai,sanPhamSua.getId(), str_linkytb, Integer.parseInt(str_slkho))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if (messageModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Sửa sản phẩm thành công", Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Không thành công", Toast.LENGTH_LONG).show();
                                        }
                                        Intent intent = new Intent(getApplicationContext(), QuanLyActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            )
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("log", "onActivityResult: " + mediaPath);
    }

    private void themsanpham() {
        String str_ten = binding.themspTen.getText().toString().trim();
        String str_gia = binding.themspGia.getText().toString().trim();
        String str_mota = binding.themspMota.getText().toString().trim();
        String str_hinhanh = binding.themspHinhanh.getText().toString().trim();
        String str_linkytb = binding.themspLink.getText().toString().trim();
        String str_slkho = binding.themspSoluongkho.getText().toString();
        if (TextUtils.isEmpty(str_linkytb) || TextUtils.isEmpty(str_slkho) || TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) | TextUtils.isEmpty(str_hinhanh) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui Lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
        }else{
            compositeDisposable.add(apiBanHang.themsp(str_ten,str_gia,str_hinhanh,str_mota,(loai), str_linkytb, Integer.parseInt(str_slkho))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                          messageModel -> {
                                if (messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), QuanLyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMassage(), Toast.LENGTH_LONG).show();
                                }
                          },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    )
            );
        }
    }

   private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
       if (cursor == null) {
           result = uri.getPath();
       }else {
           cursor.moveToFirst();
           int index = cursor.getColumnIndex(MediaStore.Images. ImageColumns.DATA);
           result = cursor.getString(index);
           cursor.close();
       }

        return result;
   }
    private void uploadMultipleFiles() {
        Uri uri= Uri.parse (mediaPath);
        File file = new File(getPath(uri));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody. Part fileToUpload1 = MultipartBody. Part.createFormData( "file", file.getName(), requestBody1);
        Call <MessageModel> call = apiBanHang.uploadFile(fileToUpload1);
        call.enqueue(new Callback< MessageModel >() {
            @Override
            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        binding.themspHinhanh.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMassage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarthemsp);
        themsp_loai = findViewById(R.id.themsp_loai);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}