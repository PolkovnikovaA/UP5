package com.example.up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class AdapterHoriz extends RecyclerView.Adapter<AdapterHoriz.ViewHolder> {
    private Context mContext;
    List<MaskaHoriz> maskaHorizs;

    public AdapterHoriz(Context mContext, List<MaskaHoriz> maskaHorizs)
    {
        this.mContext = mContext;
        this.maskaHorizs = maskaHorizs;
    }

    @NonNull
    @Override
    public AdapterHoriz.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterHoriz.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.maska_horiz, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoriz.ViewHolder holder, int position) {
        final MaskaHoriz modal = maskaHorizs.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.logo);
        }
        else
        {
            new AdapterHoriz.DownloadImageTask((ImageView) holder.image)
                    .execute(modal.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return maskaHorizs.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Horiz_nastr);
            image = itemView.findViewById(R.id.Horiz_Image);
        }
    }
}