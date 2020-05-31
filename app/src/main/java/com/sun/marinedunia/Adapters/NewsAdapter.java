package com.sun.marinedunia.Adapters;

import android.app.ProgressDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sun.marinedunia.Models.NewsItem;
import com.sun.marinedunia.R;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewholder> {
    private List<NewsItem> newsitemModelList;
    private Context context;
    private ProgressDialog progressDialog;
    private OnItemClickListner onItemClickListener;

    public NewsAdapter(List<NewsItem> categoryModelList, Context context) {
        this.newsitemModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new viewholder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int position) {
        viewholder.setData(newsitemModelList.get(position).getImageurl(), newsitemModelList.get(position).getTitle(), newsitemModelList.get(position).getNewsurl());
    }

    @Override
    public int getItemCount() {
        return newsitemModelList.size();
    }

    public void setOnItemClickListener(OnItemClickListner onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
     public interface OnItemClickListner{
        void onItemClick(View view,int position);
     }
    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView NewsImage;
        private ProgressBar progress_load_photo;
        OnItemClickListner onItemClickListner;
        public viewholder(@NonNull View itemView,OnItemClickListner onItemClickListner) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.News_Title);
            NewsImage = itemView.findViewById(R.id.news_img);
            progress_load_photo = itemView.findViewById(R.id.progress_load_photo);
            this.onItemClickListner = onItemClickListner;

        }

        private void setData(final String imageurl, final String Newstitle, final String NewsUrl) {
            Glide.with(itemView.getContext()).load(imageurl).placeholder(R.drawable.ic_picture_as_pdf_black_24dp).into(NewsImage);
            this.title.setText(Newstitle);
            progress_load_photo.setVisibility(View.GONE);


        }

        @Override
        public void onClick(View v) {
    onItemClickListner.onItemClick(v,getAdapterPosition());
        }
    }




}

