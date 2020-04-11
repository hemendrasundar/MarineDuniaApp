package com.sun.marinedunia.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sun.marinedunia.Activities.BooksActivity;
import com.sun.marinedunia.R;
import com.sun.marinedunia.Models.BookCategory;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BooksCategoryAdapter extends RecyclerView.Adapter<BooksCategoryAdapter.viewholder> {
   private List<BookCategory> categoryModelList;

    public BooksCategoryAdapter(List<BookCategory> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books_category_item,viewGroup,false);
   return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int position) {
        viewholder.setData(categoryModelList.get(position).getCatUrl(),categoryModelList.get(position).getCatName());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();


    }

    class viewholder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;
        private TextView title;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.books_category_circle_iv);
            title = itemView.findViewById(R.id.books_category_title_tv);
        }
        private void setData(String url, final String title){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent setIntent = new Intent(itemView.getContext(), BooksActivity.class);
                    setIntent.putExtra("catname",title);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}
