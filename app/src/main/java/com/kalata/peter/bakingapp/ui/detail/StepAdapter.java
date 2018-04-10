package com.kalata.peter.bakingapp.ui.detail;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.local.entity.StepEntity;
import com.kalata.peter.bakingapp.ui.main.RecipeViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepAdapter extends RecyclerView.Adapter {

    List<StepEntity> steps;
    private final ActionListener actionListener;
    private int selectedPosition = -1;
    private boolean isTablet = false;

    public StepAdapter(ActionListener actionListener, List<StepEntity> steps, boolean isTablet){
        this.actionListener = actionListener;
        this.steps = steps;
        this.isTablet = isTablet;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step, viewGroup, false);
        return new StepViewHolder(itemView, actionListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ((StepViewHolder) holder).bindData(steps.get(i), i);
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_name)
        TextView tvStepName;
        @BindView(R.id.cl_wrapper)
        ConstraintLayout clWrapper;
        @BindView(R.id.iv_thumbnail)
        ImageView ivThumbnail;

        private StepAdapter.ActionListener actionListener;
        private StepEntity step;
        private ColorGenerator generator = ColorGenerator.MATERIAL;

        StepViewHolder(View itemView, final StepAdapter.ActionListener actionListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.actionListener = actionListener;
        }

        public void bindData(StepEntity step, int position) {
            clWrapper.setSelected(isTablet && position == selectedPosition);
            this.step = step;
            tvStepName.setText(step.getShortDescription());
            setupThumbnail(step.getThumbnailURL());
        }

        private void setupThumbnail(String thumbnailUrl) {
            if (!TextUtils.isEmpty(thumbnailUrl)) {
                Glide.with(itemView.getContext())
                        .load(step.getThumbnailURL())
                        .centerCrop()
                        .into(ivThumbnail);
            } else {
                String letter = String.valueOf(step.getShortDescription().charAt(0));
                int color = generator.getColor(step.getShortDescription());
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(letter, color);
                ivThumbnail.setImageDrawable(drawable);
            }
        }

        @OnClick(R.id.cl_wrapper)
        void onStepClick() {
            if (isTablet && selectedPosition == getAdapterPosition()) return;
            selectedPosition = getAdapterPosition();
            actionListener.onStepClick(step, getAdapterPosition());
            notifyDataSetChanged();
        }

    }

    public interface ActionListener {

        void onStepClick(StepEntity step, int position);

    }



}