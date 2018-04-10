package com.vitaliibonar.sunrisesunset.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.vitaliibonar.sunrisesunset.model.PlaceSuggestion;
import com.vitaliibonar.sunrisesunset.viewholder.SuggestionViewHolder;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {

    private List<PlaceSuggestion> suggestions;

    private ItemClickedCallback itemClickedCallback;

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SuggestionViewHolder suggestionViewHolder = SuggestionViewHolder.inflate(parent);

        suggestionViewHolder.setClickListener(v -> {
            if (itemClickedCallback != null) {
                itemClickedCallback.onClick(suggestions.get(suggestionViewHolder.getAdapterPosition()).getId());
            }
        });

        return suggestionViewHolder;
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {
        holder.bind(suggestions.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (suggestions == null) {
            return 0;
        }

        return suggestions.size();
    }

    public void setItemClickedCallback(ItemClickedCallback itemClickedCallback) {
        this.itemClickedCallback = itemClickedCallback;
    }

    public void setSuggestions(List<PlaceSuggestion> suggestions) {
        this.suggestions = suggestions;
        notifyDataSetChanged();
    }

    public interface ItemClickedCallback {
        void onClick(String id);
    }
}
