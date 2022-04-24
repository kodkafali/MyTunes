package com.mytunes.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytunes.R;
import com.mytunes.helper.Utility;
import com.mytunes.impl.OnItemClick;
import com.mytunes.model.SearchData;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter<MasterAdapter.HeaderViewHolder> {

    private final List<SearchData> mValues;
    private final Context mContext;
    private final OnItemClick mClick;

    public MasterAdapter(Context context, OnItemClick onItemClick, List<SearchData> values){
        this.mContext = context;
        this.mClick = onItemClick;
        this.mValues = values;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final SearchData item = mValues.get(position);

        Picasso.get().load(item.artworkUrl100).into(holder.gridIcon);
        holder.title.setText(item.artistName);
        holder.content.setText(Utility.getInstance(mContext).getSimpleDate(item.releaseDate));

        holder.itemView.setOnClickListener(listener -> mClick.onItem(item));
    }

    @Override
    public long getHeaderId(int position) {
        return mValues.get(position).wrapperType.charAt(0);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_header, parent, false);
        return new HeaderViewHolder(headerView);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int i) {
        String headerText = mValues.get(i).wrapperType.toUpperCase();
        viewHolder.mTxtHeader.setText(headerText);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<SearchData> getData(){
        return mValues;
    }

    public void updateData(List<SearchData> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(int position, SearchData item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        mValues.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView content;
        private final ImageView gridIcon;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            content = itemView.findViewById(R.id.txtContent);
            gridIcon = itemView.findViewById(R.id.imgIcon);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView mTxtHeader;

        public HeaderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mTxtHeader = itemView.findViewById(R.id.list_item_section_text);
        }
    }

}
