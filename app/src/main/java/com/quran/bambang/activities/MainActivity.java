package com.quran.bambang.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quran.bambang.R;
import com.quran.bambang.adapter.SurahAdapter;
import com.quran.bambang.api.ApiClient;
import com.quran.bambang.api.ApiInterface;
import com.quran.bambang.model.Cek;
import com.quran.bambang.model.Data;
import com.quran.bambang.model.Surah;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String arabic = "quran-uthmani";
    private static final String indo = "id.indonesian";
//    pembuatan class final untuk jenis tulisan dan bahasa

    private List<Surah> surahsArabic = new ArrayList<>();
    private List<Surah> surahsIndo = new ArrayList<>();
//    class untuk surah dan terjemahan

    ProgressDialog loadingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingData = new ProgressDialog(this);
        loadingData.setTitle("Mohon tunggu...");
        loadingData.setCancelable(false);
        loadingData.setMessage("Sedang mengambil data dari API");
//        proses loading

        RecyclerView recyclerSurah = findViewById(R.id.surah_list);
//        penapilan surah secara list scrolldown
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        penyusunan surah
        recyclerSurah.setHasFixedSize(true);
//        pembuatan ukuran
        recyclerSurah.setLayoutManager(layoutManager);
//        pembuatan layout susai layoutmanager

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
//        pengambilan API

        Call<Cek> call = apiInterface.getCek(arabic);
        Call<Cek> callIndo = apiInterface.getCek(indo);
//        pemaggilan API

        getDataListArabic(recyclerSurah, call);
        getDataTarjim(callIndo);
//        pengambilan data dari API
    }

    private void getDataTarjim(Call<Cek> callIndo) {
        callIndo.enqueue(new Callback<Cek>() {
            @Override
            public void onResponse(Call<Cek> call, Response<Cek> response) {
                Data data = response.body().getData();
                surahsIndo = data.getSurahs();

            }
//            pemanggilan terjemahan

            @Override
            public void onFailure(Call<Cek> call, Throwable t) {
                Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
//            respon jika gagal
        });
    }

    private void getDataListArabic(final RecyclerView recyclerSurah, Call<Cek> call) {
        loadingData.show();
        call.enqueue(new Callback<Cek>() {
            @Override
            public void onResponse(Call<Cek> call, Response<Cek> response) {
                Data data = response.body().getData();
                surahsArabic = data.getSurahs();
//                pemanggilan surah
                SurahAdapter surahAdapter = new SurahAdapter(MainActivity.this, surahsArabic, surahsIndo);
                recyclerSurah.setAdapter(surahAdapter);
                loadingData.dismiss();
//                penggunaan adapter retrofit
            }

            @Override
            public void onFailure(Call<Cek> call, Throwable t) {
                loadingData.dismiss();
                Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
//                respon jika eror
            }
        });
    }
}
