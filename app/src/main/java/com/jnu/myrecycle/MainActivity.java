//package com.jnu.myrecycle;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.jnu.myrecycle.data.Book;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ActivityResultLauncher<Intent>itemLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        RecyclerView recyclerView = findViewById(R.id.recycle_view_books);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        ArrayList<Book> books =new ArrayList<>();
//        books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));
//        books.add(new Book("软件管理项目案例教程（第4版）", 120, R.drawable.book_2));
//        books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
//        books.add(new Book("油画", 1024, R.drawable.a_oil_painting));
//
//        CustomAdapter adapter = new CustomAdapter(books);
//        recyclerView.setAdapter(adapter);
//
//        registerForContextMenu(recyclerView);
//
//        itemLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        String name = data.getStringExtra("name");
//                        String price_text = data.getStringExtra("price");
//                        Double price=Double.parseDouble(price_text);
//                        books.add(new Book(name, price, R.drawable.book_no_name));
//                        adapter.notifyItemInserted(books.size());
//                    }
//                    else if (result.getResultCode() == RESULT_CANCELED) {
//                        Toast.makeText(MainActivity.this, "哈哈哈哈哈哈哈哈哈", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//    private String getAdapterPosition() {
//        return "position:" + this.getAdapterPosition();
//    }
//
//
//    public boolean onContextItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case 0:
//                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Intent intent = new Intent(MainActivity.this,
//                        shop_item_MainActivity.class);
//                itemLauncher.launch(intent);
//                break;
//            case 2:
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder()
//                        +"修改", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder()
//                        +"\n加入购物车", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onContextItemSelected(item);
//        }
//        return true;
//    }
//    public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.ViewHolder>{
//        private ArrayList<Book> BOOK_Shop_item;
//        public class ViewHolder extends RecyclerView.ViewHolder
//                implements View.OnCreateContextMenuListener {
//            private final TextView item_name;
//            private final TextView item_price;
//            private final ImageView item_image;
//            public ViewHolder(View shop_item_view) {
//                super(shop_item_view);
//                this.item_name = shop_item_view.findViewById(R.id.text_view_book_title);
//                this.item_price = shop_item_view.findViewById(R.id.item_price);
//                this.item_image = shop_item_view.findViewById(R.id.image_view_book_cover);
//                shop_item_view.setOnCreateContextMenuListener(this);
//            }
//            public void onCreateContextMenu(ContextMenu menu, View v,
//                                            ContextMenu.ContextMenuInfo menuInfo){
//                menu.setHeaderTitle("具体操作");
//                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
//                menu.add(0,1,this.getAdapterPosition(),"添加"+this.getAdapterPosition());
//                menu.add(0,2,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
//                menu.add(0,3,this.getAdapterPosition(),"【购买】"+this.getAdapterPosition());
//            }
//            public TextView getItemName(){
//                return item_name;
//            }
//            public TextView getItemPrice(){
//                return item_price;
//            }
//            public ImageView getItemImage(){return item_image;}
//        }
//        public CustomAdapter(ArrayList<Book> dataSet) {
//            BOOK_Shop_item = dataSet;
//        }
//
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).
//                    inflate(R.layout.shop_item_row, viewGroup, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
//            holder.getItemName().setText(BOOK_Shop_item.get(position).getName());
//            holder.getItemPrice().setText(BOOK_Shop_item.get(position).getPrice()+"元");
//            holder.getItemImage().setImageResource(BOOK_Shop_item.get(position).getImageId());
//        }
//
//        @Override
//        public int getItemCount() {
//            return BOOK_Shop_item.size();
//        }
//    }
//
//}

package com.jnu.myrecycle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myrecycle.view.WhackAMoleView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public String []tabName={"图书","新闻","地图","时钟","游戏"};
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager2= findViewById(R.id.viewPaGet2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.setOffscreenPageLimit(5);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabName[(position)])).attach();

    }

    public class FragmentAdapter extends FragmentStateAdapter {
        private final int size_tab = tabName.length;
        public FragmentAdapter(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
            super(supportFragmentManager,lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new BookFragment();
                case 1:
                    return new WebFragment();
                case 2:
                    return new BaiduMapFragment();
                case 3:
                    return new ClockviewFragment();
                case 4:
                    return new GameFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return size_tab;
        }
    }
}