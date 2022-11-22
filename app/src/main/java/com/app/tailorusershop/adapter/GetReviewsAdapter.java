package com.app.tailorusershop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.activity.AddReviewActivity;
import com.app.tailorusershop.activity.ReviewListActivity;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.ItemReviewListAdapterBinding;
import com.app.tailorusershop.responses.GetReviewListResponse;

import java.util.List;

public class GetReviewsAdapter extends RecyclerView.Adapter<GetReviewsAdapter.HOLDER> {
        private Context context;
        private List<GetReviewListResponse.ReviewData> reviewData;
        private EditReviewListener editReviewListener;

    public GetReviewsAdapter(Context context, List<GetReviewListResponse.ReviewData> reviewData) {
        this.context = context;
        this.reviewData = reviewData;
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemReviewListAdapterBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, int position) {

        holder.binding.timelyRating.setRating(Float.parseFloat(reviewData.get(position).getTimelyOrderCompletionRating()));
        holder.binding.fittingRating.setRating(Float.parseFloat(reviewData.get(position).getProductFittingRating()));
        holder.binding.creationRating.setRating(Float.parseFloat(reviewData.get(position).getOrderCreationRating()));
        holder.binding.txtComment.setText(reviewData.get(position).getComments());
        holder.binding.imgEdit.setOnClickListener(view->{editReviewListener.editReView(reviewData.get(position));});
        holder.binding.imgDel.setOnClickListener(view -> {deleteDialog("9");});

    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemReviewListAdapterBinding binding;
        public HOLDER(@NonNull ItemReviewListAdapterBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }


    private void deleteDialog(String reviewId) {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Delete Review");
        binding.txtDelMsg.setText("Are you sure you want to delete Review ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((ReviewListActivity)context).deleteReview(reviewId);
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    public void setOnEditReviewListener(EditReviewListener onEditReviewListener)
    {

        editReviewListener=onEditReviewListener;
    }

    public interface EditReviewListener
    {
        public void editReView(GetReviewListResponse.ReviewData reviewData);
    }
}
