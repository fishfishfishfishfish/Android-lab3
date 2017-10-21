package com.chan.android_lab3;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    List<Map<String, Object>> ShoppingList = new ArrayList<>();
    List<Map<String, Object>> GoodsList = new ArrayList<>();

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
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, ShoppingList,R.layout.shoppinglist_layout,new String[]{"abbr","name", "price"},new int[]{R.id.abbr,R.id.name,R.id.price});
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
                return false;
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

        final ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(goodslistAdapter);
        animationAdapter.setDuration(1000);
        goodsRecyclerView.setAdapter(animationAdapter);
        goodsRecyclerView.setItemAnimator(new OvershootInLeftAnimator(1f));
        goodsRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        goodslistAdapter.setmOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(int position) {
                GoodsList.remove(position);
                goodslistAdapter.notifyItemChanged(position);
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

    //购物车需要的List在此初始化
    private void initShoppingList()
    {
        String[] goodName = new String[]{"购物车","Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                                            "Mcvitie's 饼干", "Ferrero Rocher","Maltesers","Lindt","Borggreve"};
        String[] goodPrice = new String[]{"价格","¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00",
                "¥ 14.90", "¥ 132.59","¥ 141.43","¥ 139.43","¥ 28.90"};
        Map<String, Object> temp0 = new LinkedHashMap<>();
        temp0.put("abbr", "*");
        temp0.put("name", goodName[0]);
        temp0.put("price", goodPrice[0]);
        ShoppingList.add(temp0);
        for(int i = 1; i < 10; i++)
        {
            Map<String, Object> temp1 = new LinkedHashMap<>();
            temp1.put("abbr", goodName[i].substring(0,1));
            temp1.put("name", goodName[i]);
            temp1.put("price", goodPrice[i]);
            ShoppingList.add(temp1);
        }
    }
    //商品列表在此初始化
    private void initGoodsList()
    {
        String[] goodName = new String[]{"购物车","Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                "Mcvitie's 饼干", "Ferrero Rocher","Maltesers","Lindt","Borggreve"};
        for(int i = 1; i < 10; i++)
        {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("abbr", goodName[i].substring(0,1));
            temp.put("name", goodName[i]);
            GoodsList.add(temp);
        }
    }

}
