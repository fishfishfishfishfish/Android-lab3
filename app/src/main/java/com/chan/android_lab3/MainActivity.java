package com.chan.android_lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    List<Map<String, Object>> ShoppingList = new ArrayList<>();
    List<Map<String, Object>> GoodsList = new ArrayList<>();
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //购物车的对话框
        final AlertDialog.Builder shoppinglist_alertdialog = new AlertDialog.Builder(this);
        shoppinglist_alertdialog.setTitle("移除商品")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        //购物车，使用ListView和SimpleAdapter
        initShoppingList();//初始化购物车需要的List
        final ListView shoppingListView = (ListView) findViewById(R.id.shoppinglist);
        simpleAdapter = new SimpleAdapter(this, ShoppingList,R.layout.shoppinglist_layout,new String[]{"abbr","name", "price"},new int[]{R.id.abbr,R.id.name,R.id.price});
        shoppingListView.setAdapter(simpleAdapter);
        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos= position;
                if(pos != 0)
                {
                    shoppinglist_alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShoppingList.remove(pos);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    }).setMessage("从购物车移除"+ShoppingList.get(pos).get("name")+"?")
                            .create()
                            .show();
                }
                return true;
            }
        });
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    String chose_name = ShoppingList.get(i).get("name").toString();
                    Intent intent = new Intent(MainActivity.this, detail.class);
                    intent.putExtra("goodsName", chose_name);
                    startActivityForResult(intent, 1);
                }
            }
        });

        initGoodsList();//初始化商品列表
        final RecyclerView goodsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //商品列表的Adapter
        final CommonAdapter goodslistAdapter = new CommonAdapter(this, R.layout.item, GoodsList)
        {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView name = holder.getView(R.id.name);
                name.setText(s.get("name").toString());
                TextView abbr = holder.getView(R.id.abbr);
                abbr.setText(s.get("abbr").toString());
            }
        };

//        goodsRecyclerView.setAdapter(goodslistAdapter);
//        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(goodslistAdapter);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(goodslistAdapter);
        animationAdapter.setDuration(500);
        goodsRecyclerView.setAdapter(animationAdapter);
        goodsRecyclerView.setItemAnimator(new LandingAnimator());
        goodslistAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String chose_name = GoodsList.get(position).get("name").toString();
                Intent intent = new Intent(MainActivity.this, detail.class);
                intent.putExtra("goodsName", chose_name);
                startActivityForResult(intent,1);
            }

            @Override
            public boolean onLongClick(int position) {
                String index = Integer.toString(position);
                GoodsList.remove(position);
                goodslistAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this,"移除第"+index+"个商品",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        final FloatingActionButton SwitchBtn = (FloatingActionButton)findViewById(R.id.switch_button);
        SwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsRecyclerView.getVisibility() == View.VISIBLE)
                {
                    goodsRecyclerView.setVisibility(View.GONE);
                    shoppingListView.setVisibility(View.VISIBLE);
                    SwitchBtn.setImageResource(R.drawable.mainpage);
                }
                else if(shoppingListView.getVisibility() == View.VISIBLE)
                {
                    goodsRecyclerView.setVisibility(View.VISIBLE);
                    shoppingListView.setVisibility(View.GONE);
                    SwitchBtn.setImageResource(R.drawable.shoplist);//如何设置图片为background?
                }
            }
        });
    }//end OnCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String rev_name = data.getStringExtra("name");
                String rev_price = data.getStringExtra("price");
                String rev_count = data.getStringExtra("count");
                if(rev_name != null && rev_price != null && rev_count != "0")
                {
                    int count = Integer.parseInt(rev_count);
                    for(int i = 0; i < count; i++)
                    {
                        Map<String,Object> temp = new LinkedHashMap<>();
                        temp.put("abbr", rev_name.substring(0,1));
                        temp.put("name", rev_name);
                        temp.put("price", rev_price);
                        ShoppingList.add(temp);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }
    //购物车需要的List在此初始化
    private void initShoppingList()
    {
        String[] goodName = new String[]{"购物车"};
        String[] goodPrice = new String[]{"价格"};
        Map<String, Object> temp0 = new LinkedHashMap<>();
        temp0.put("abbr", "*");
        temp0.put("name", goodName[0]);
        temp0.put("price", goodPrice[0]);
        ShoppingList.add(temp0);
    }
    //商品列表在此初始化
    private void initGoodsList()
    {
        String[] goodName = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                "Mcvitie's 饼干", "Ferrero Rocher","Maltesers","Lindt","Borggreve"};
        for(int i = 0; i < 10; i++)
        {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("abbr", goodName[i].substring(0,1));
            temp.put("name", goodName[i]);
            GoodsList.add(temp);
        }
    }

}
