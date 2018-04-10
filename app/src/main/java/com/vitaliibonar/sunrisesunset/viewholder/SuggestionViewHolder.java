package com.vitaliibonar.sunrisesunset.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaliibonar.sunrisesunset.R;
import com.vitaliibonar.sunrisesunset.databinding.ItemSuggestionBinding;

public class SuggestionViewHolder extends RecyclerView.ViewHolder {

    private ItemSuggestionBinding binding;

    public static SuggestionViewHolder inflate(ViewGroup parent) {
        return new SuggestionViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_suggestion,
                parent,
                false));
    }

    public void bind(String text) {
        binding.tvSearchResult.setText(text);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        binding.root.setOnClickListener(clickListener);
    }

    private SuggestionViewHolder(ItemSuggestionBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }
}