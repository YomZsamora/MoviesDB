package com.adzumi.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adzumi.moviesdb.R;
import com.adzumi.moviesdb.models.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.CustomViewHolder> {

    private List<Result> mPopular;
    private Context context;

    public PopularMoviesAdapter(Context context,List<Result> mPopular){
        this.context = context;
        this.mPopular = mPopular;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        @BindView(R.id.movieTitle) TextView mMovieTitle;
        @BindView(R.id.overview) TextView mOverview;
        @BindView(R.id.popularity) TextView mPopularity;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_popular, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        holder.eventDescription.setMovementMethod(new ScrollingMovementMethod());
        holder.mMovieTitle.setText(mPopular.get(position).getTitle());
        holder.mOverview.setText(mPopular.get(position).getOverview());
        holder.mPopularity.setText(mPopular.get(position).getPopularity().toString());

//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(mEvents.get(position).getLogo().getUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return mPopular.size();
    }
}
