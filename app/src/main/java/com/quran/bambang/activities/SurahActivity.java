package com.quran.bambang.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quran.bambang.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quran.bambang.adapter.AyatAdapter;
import com.quran.bambang.model.Ayat;

import java.lang.reflect.Type;
import java.util.List;

public class SurahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        pebuatan bundle save pada oncreate
        super.onCreate(savedInstanceState);
//        pemanggilan kelas oncreate
        setContentView(R.layout.activity_surah_content);
//        konten view surah

        Toolbar mToolbarSurah = findViewById(R.id.mToolbarSurah);
//        fungsi pencarian surah
        setSupportActionBar(mToolbarSurah);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        pembuatan scroll

        String json = getIntent().getStringExtra("jsonlist");
        String jsonIndo = getIntent().getStringExtra("jsonlistIndo");
        String jsonTitle = getIntent().getStringExtra("jsonTitle");
        Type type = new TypeToken<List<Ayat>>() {
        }.getType();
//        pengambilan data Json retrofit

        TextView txtTitleSurah = findViewById(R.id.txtTitleSurah);
        txtTitleSurah.setText(jsonTitle);
        txtTitleSurah.findViewById(R.id.txtTitleSurah);
        txtTitleSurah.setText(jsonTitle);
//        penampilan title surah

        Gson gson = new Gson();
        List<Ayat> ayatList = gson.fromJson(json, type);
        List<Ayat> ayatListIndo = gson.fromJson(jsonIndo, type);
//        parsing json

        RecyclerView recyclerAyat = findViewById(R.id.ayat_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        AyatAdapter ayatAdapter = new AyatAdapter(this, ayatList, ayatListIndo);
//        pemanggilan ayat dari retrofit
        recyclerAyat.setLayoutManager(layoutManager);
        recyclerAyat.setHasFixedSize(true);
        recyclerAyat.setAdapter(ayatAdapter);
//        layot ayat setelah di panggil
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
