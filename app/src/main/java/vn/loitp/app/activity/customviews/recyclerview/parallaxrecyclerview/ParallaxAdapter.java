package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;

public class ParallaxAdapter extends RecyclerView.Adapter {

    private Context context;

    ParallaxAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_parallax, parent, false);
        return new ParallaxHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    private static class ParallaxHolder extends RecyclerView.ViewHolder {
        ParallaxHolder(View itemView) {
            super(itemView);
        }
    }
}
