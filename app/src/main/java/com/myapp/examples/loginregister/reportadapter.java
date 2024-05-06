package com.myapp.examples.loginregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class reportadapter extends RecyclerView.Adapter<reportadapter.ReportViewHolder> {

    private Context mCtx;
    private List<report> reportList;

    public reportadapter(Context mCtx, List<report> reportList) {
        this.mCtx = mCtx;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.report_list, null);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        report report = reportList.get(position);

        holder.textViewTitle.setText(report.getReportDesc());
        holder.textViewShortDesc.setText(report.getReportDate());
        holder.textViewRating.setText(report.getReportType());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewShortDesc, textViewRating ;
        public ReportViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);

        }
    }
}
