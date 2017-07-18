package com.example.navee.smartroom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by navee on 6/5/2017.
 */

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    List<item> list;
    Context c;
    public adapter(List<item> list,Context c) {
        this.list=list;
        this.c=c;
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }
    public void tempremove(int posi,item i){
        list.remove(posi);
        list.add(posi,i);
        notifyItemInserted(list.size());
        notifyItemRangeChanged(posi,list.size());
    }

    public void addItem(item i) {
        list.add(i);
        notifyItemInserted(list.size());
    }
    public void addItemcus(item i,int position){
        list.add(i);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, list.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item it=list.get(position);
        holder.name.setText(it.getLabel());
        holder.plug.setChecked(it.getBool());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        Switch plug;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.textView3);
            plug=(Switch)itemView.findViewById(R.id.switch1);
            itemView.setOnClickListener(this);
            plug.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(v.getId()==plug.getId()){
                boolean bool=plug.isChecked();
                if(bool==true){
                    int pos=getAdapterPosition();
                    item it=list.get(pos);
                    String text=it.getLabel();
                    Toast.makeText(c,"switched on at"+ pos +" "+text, Toast.LENGTH_SHORT).show();
                }
                else{
                    int pos=getAdapterPosition();
                    Toast.makeText(c, "switched off at"+pos, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
