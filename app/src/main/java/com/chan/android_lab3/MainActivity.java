package com.chan.android_lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<Map<String, Object>> ShoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initShoppingList();
        ListView shoppingListView = (ListView) findViewById(R.id.shoppinglist);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, ShoppingList,R.layout.shoppinglist_layout,new String[]{"abbr","name", "price"},new int[]{R.id.abbr,R.id.name,R.id.price});
        shoppingListView.setAdapter(simpleAdapter);
        shoppingListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                
                return false;
            }
        });
    }

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

}
