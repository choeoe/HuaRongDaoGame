package com.example.hrdgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.example.hrdgame.App.getContext;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.start_game)
    public Button starter;
    @BindView(R.id.start_game1)
    public Button starter1;
    @BindView(R.id.rank)
    public Button rank;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        starter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                List<Rank> ranks = DataSupport.findAll(Rank.class);
//                for(Rank rank : ranks){
//                    Log.d("tanglei","p "+rank.getPlayer());
//                    Log.d("tanglei","c "+rank.getCreated_at());
//                    Log.d("tanglei",String.valueOf(rank.getSteps()));
//                    Log.d("tanglei",String.valueOf(rank.getTime()));
//                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                final View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.rank_list, null);
                mRecyclerView = view.findViewById(R.id.rank_list_body);
                builder.setView(view);
                RankAdapter adapter = new RankAdapter(LoginActivity.this,ranks);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(LoginActivity.this));
                builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });
    }
}
