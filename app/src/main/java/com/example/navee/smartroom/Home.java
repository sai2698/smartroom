package com.example.navee.smartroom;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

public class Home extends AppCompatActivity {
      RecyclerView rview;
     Toolbar tool;
     int log;
      adapter adapter;
    List<item> list;
    private Paint p = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rview=(RecyclerView)findViewById(R.id.recyclerview);
        rview.setHasFixedSize(true);
        tool=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Smart Room");
        rview.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        if(savedInstanceState != null){
            ArrayList items = savedInstanceState.getStringArrayList("items");
            list.addAll(items);
            adapter.notifyDataSetChanged();
        }
        else{
        /*
            for(int i=0;i<3;i++){
             //if(i%2==0){
            item it=new item("item"+i,true);
                 list.add(it);
            // }
             //else{
              //   item it=new item("item"+i,false);
                // list.add(it);
           //  }
        }
        */
        }
        adapter =new adapter(list,this);
        rview.setAdapter(adapter);
        Intent intent = getIntent();
        String port=intent.getStringExtra("host");
        String host=intent.getStringExtra("port");
        Toast.makeText(this,host+":"+port, Toast.LENGTH_SHORT).show();
        ItemTouchHelper.SimpleCallback touch=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveitem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT){
                    item i=list.get(position);
                    String label=i.getLabel();
                    showdeledialog(position,label);
                } else {
                    item i=list.get(position);
                    String label=i.getLabel();
                    editdialog(position,label);
                }
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touch);
        itemTouchHelper.attachToRecyclerView(rview);


    }

    private void edititem(int posi,String k) {
        item u=new item(k,false);
        adapter.tempremove(posi,u);
    }

    private void editdialog(final int posi, final String label) {
        final Dialog dialog = new Dialog(this);
//tell the Dialog to use the dialog.xml as it's layout description
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setTitle("Edit the Item");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        TextView t=(TextView)dialog.findViewById(R.id.textView6);
        t.setText("Are you want to edit this name ?");
        Button cancel = (Button) dialog.findViewById(R.id.button4);
        Button add=(Button)dialog.findViewById(R.id.button5);
        add.setText("Edit");
        final EditText text=(EditText) dialog.findViewById(R.id.editText4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=text.getText().toString();
                edititem(posi,s);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edititem(posi,label);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      switch (item.getItemId()){
          case R.id.action_settings:
              Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
              showdialog("Please input name of the appliance","Add");
              break;
      }
        return super.onOptionsItemSelected(item);
    }

    private void moveitem(int adapterPosition, int adapterPosition1) {
        item m=list.get(adapterPosition);
        list.remove(m);
        list.add(adapterPosition1,m);
        adapter.notifyItemMoved(adapterPosition,adapterPosition1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList array= (ArrayList) list;
        outState.putStringArrayList("items",array);
    }

    public void showdialog(String s,String k){
        final Dialog dialog = new Dialog(this);
//tell the Dialog to use the dialog.xml as it's layout description
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setTitle("Add an Item");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        TextView t=(TextView)dialog.findViewById(R.id.textView6);
        t.setText(s);
        Button cancel = (Button) dialog.findViewById(R.id.button4);
        Button add=(Button)dialog.findViewById(R.id.button5);
        add.setText(k);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text=(EditText) dialog.findViewById(R.id.editText4);
                String s=text.getText().toString();
                add(s);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void add(String s) {
        item i=new item(s,false);
        adapter.addItem(i);
        Toast.makeText(Home.this, s+" "+"added", Toast.LENGTH_SHORT).show();
    }

    public void showdeledialog(final int position,final String label){
        final Dialog dialog = new Dialog(this);
//tell the Dialog to use the dialog.xml as it's layout description
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setTitle("Add an Item");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button cancel = (Button) dialog.findViewById(R.id.button2);
        Button dele=(Button)dialog.findViewById(R.id.button3);
        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeItem(position);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edititem(position,label);
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}

