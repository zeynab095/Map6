package com.example.user.map6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView; //how to import recycler view in android 3.0
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by EmilKelbali on 12/7/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    String data[];
    Context ctx;
    public MyAdapter(Context ct, String s[]){
        ctx =ct;
        data=s;

    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflator = LayoutInflater.from(ctx);
        View myOwnView = myInflator.inflate(R.layout.activity_recycler,parent,false);
        return new MyHolder(myOwnView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
    holder.b.setText(data[position]);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(ctx,SetAlarmActivity.class);
                i1.putExtra("name",data[position]);
                ctx.startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        Button b;
        public MyHolder(View itemView) {
            super(itemView);
            b= (Button)itemView.findViewById(R.id.b1);
        }

    }
}
