/*
 * This file is part of Grocy Android.
 *
 * Grocy Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Grocy Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Grocy Android. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2020-2021 by Patrick Zedler & Dominic Zedler
 */

package xyz.zedler.patrick.grocy.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.zedler.patrick.grocy.R;
import xyz.zedler.patrick.grocy.model.ProductGroup;

public class ProductGroupAdapter extends RecyclerView.Adapter<ProductGroupAdapter.ViewHolder> {

    private final static String TAG = ProductGroupAdapter.class.getSimpleName();

    private ArrayList<ProductGroup> productGroups;
    private int selectedId;
    private ProductGroupAdapterListener listener;

    public ProductGroupAdapter(
            ArrayList<ProductGroup> productGroups,
            int selectedId,
            ProductGroupAdapterListener listener
    ) {
        this.productGroups = productGroups;
        this.selectedId = selectedId;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer;
        private TextView textViewName;
        private ImageView imageViewSelected;

        public ViewHolder(View view) {
            super(view);

            linearLayoutContainer = view.findViewById(R.id.linear_master_edit_selection_container);
            textViewName = view.findViewById(R.id.text_master_edit_selection_name);
            imageViewSelected = view.findViewById(R.id.image_master_edit_selection_selected);
        }
    }

    @NonNull
    @Override
    public ProductGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductGroupAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.row_master_edit_selection_sheet,
                        parent,
                        false
                )
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(
            @NonNull final ProductGroupAdapter.ViewHolder holder,
            int position
    ) {
        ProductGroup productGroup = productGroups.get(holder.getAdapterPosition());

        // NAME

        holder.textViewName.setText(productGroup.getName());

        // SELECTED

        if(productGroup.getId() != -1 && selectedId != -1) {
            if(productGroup.getId() == selectedId) {
                holder.imageViewSelected.setVisibility(View.VISIBLE);
            }
        } else if(selectedId == -1 && productGroup.getId() == -1){
            holder.imageViewSelected.setVisibility(View.VISIBLE);
        }

        // CONTAINER

        holder.linearLayoutContainer.setOnClickListener(
                view -> listener.onItemRowClicked(holder.getAdapterPosition())
        );
    }

    @Override
    public int getItemCount() {
        return productGroups.size();
    }

    public interface ProductGroupAdapterListener {
        void onItemRowClicked(int position);
    }
}
