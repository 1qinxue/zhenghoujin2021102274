package com.jnu.myrecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.myrecycle.data.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView recyclerView = findViewById(R.id.recycle_view_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Book> books =new ArrayList<>();
        for(int i=0;i<10;i++) {
            books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));
            books.add(new Book("软件管理项目案例教程（第4版）", 120, R.drawable.book_2));
            books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
            books.add(new Book("油画", 1024, R.drawable.a_oil_painting));
        }
//        String []itemNames=new String[]{"信息安全教学基础","软件管理项目案例教程","无名书","油画"};
//        String []itemPrices=new String[]{"￥10"};
//        String []itemImages=new String[]{"1"};
        CustomAdapter adapter = new CustomAdapter(books);
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
//        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"删除");
        menu.add(0,1,0,"修改");
        menu.add(0,2,0,"查看详情");
    }

    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){
            case 0:
                Toast.makeText(MainActivity.this, "加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(MainActivity.this, "加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, "加入购物车", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.ViewHolder>{
        private ArrayList<Book> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView item_name;
            private final TextView item_price;
            private final ImageView item_image;
            public ViewHolder(View view) {
                super(view);
                this.item_name = view.findViewById(R.id.text_view_book_title);
                this.item_price = view.findViewById(R.id.item_price);
                this.item_image = view.findViewById(R.id.image_view_book_cover);
            }
            public TextView getItemName(){
                return item_name;
            }
            public TextView getItemPrice(){
                return item_price;
            }
            public ImageView getItemImage(){
                return item_image;
            }
        }
        public CustomAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.shop_item_row, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
            holder.getItemName().setText(localDataSet.get(position).getName());
            holder.getItemPrice().setText(localDataSet.get(position).getPrice()+"元");
            holder.getItemImage().setImageResource(localDataSet.get(position).getImageId());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}