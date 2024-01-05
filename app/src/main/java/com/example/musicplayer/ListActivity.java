package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        List();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Lấy vị trí bài hát được chọn
                int selectedPosition = position;

                // Tạo Intent để chuyển từ ListActivity sang SongsActivity
                Intent intent = new Intent(ListActivity.this, SongsActivity.class);

                // Đặt dữ liệu trong Intent (ví dụ: vị trí bài hát)
                intent.putExtra("selectedPosition", selectedPosition);

                // Mở SongsActivity
                startActivity(intent);
            }
        });

    }

    private void List() {
        //Data
        Songs.add(new ListSong("Nếu lúc đó", R.drawable.neulucdo, "Tlinh","04:24"));
        Songs.add(new ListSong("Dạy tôi cách yêu", R.drawable.daytoicachyeu, "Tlinh","04:27"));
        Songs.add(new ListSong("Tình yêu có nghĩa là gì", R.drawable.ai, "Tlinh","02:13"));
        Songs.add(new ListSong("id 072019", R.drawable.wn, " W/n","04:31"));
        Songs.add(new ListSong("3107", R.drawable.nau, " W/n, DuongG, Nâu","03:52"));
        //Adapter
        ListSongAdapter adapter1 = new ListSongAdapter();
        listView.setAdapter(adapter1);
        //su kien nhu tren

    }
    ArrayList<ListSong> Songs = new ArrayList<>();
    class ListSongAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Songs.size();
        }

        @Override
        public Object getItem(int i) {
            return Songs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //row data
            ListSong c1 = Songs.get(i);
            //row view
            View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout1_list, null);
            //gan gia tri
            ImageView imageView1 = itemView.findViewById(R.id.imageView2);
            imageView1.setImageResource(c1.img);
            TextView textView1 = itemView.findViewById(R.id.textView4);
            textView1.setText("Name : " + c1.name);
            TextView textView2 = itemView.findViewById(R.id.textView5);
            textView2.setText("Singger : " + c1.singer);
            TextView textView3 = itemView.findViewById(R.id.textView6);
            textView3.setText("⏲️ time : " + c1.time);
            return itemView;
        }

    }
    class ListSong {
        String name;
        int img;
        String singer;
        String time;


        public ListSong(String name, int img,String singer,String time) {
            this.name = name;
            this.singer = singer;
            this.img = img;
            this.time = time;

        }
    }

}


