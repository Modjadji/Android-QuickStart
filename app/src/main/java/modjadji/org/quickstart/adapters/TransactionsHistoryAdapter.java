package modjadji.org.quickstart.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import modjadji.org.quickstart.R;
import modjadji.org.quickstart.models.TransactionsHistory;
import modjadji.org.quickstart.utils.AmountFormatter;

public class TransactionsHistoryAdapter extends RecyclerView.Adapter<TransactionsHistoryAdapter.ViewHolder> {
    private SimpleDateFormat mDateFormatter;
    private List<TransactionsHistory.Transaction> mTransactions;

    public TransactionsHistoryAdapter() {
        mDateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionsHistory.Transaction transaction = mTransactions.get(position);

        holder.itemTextView.setText(transaction.Description);
        holder.dateTextView.setText(mDateFormatter.format(transaction.DatePosted));
        holder.amountTextView.setText(AmountFormatter.getInstance().formatAmount(transaction.TranAmount));
    }

    @Override
    public int getItemCount() {
        return mTransactions == null ? 0 : mTransactions.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        TextView dateTextView;
        TextView amountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTextView = (TextView) itemView.findViewById(R.id.item_textview);
            dateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            amountTextView = (TextView) itemView.findViewById(R.id.amount_textview);
        }
    }

    public void showTransactions(List<TransactionsHistory.Transaction> transactions) {
        mTransactions = transactions;
        this.notifyDataSetChanged();
    }
}
