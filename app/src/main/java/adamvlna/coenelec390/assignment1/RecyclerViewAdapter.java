package adamvlna.coenelec390.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    protected String[] events;
    protected LayoutInflater inflater;

    RecyclerViewAdapter(Context context, String[] events){
        this.inflater = LayoutInflater.from(context);
        this.events = events;
    }

    @Override
    public @NonNull ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(inflater.inflate(R.layout.recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.loggedEventTextView.setText(events[position]);
    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView loggedEventTextView;

        ViewHolder(View itemView){
            super(itemView);

            loggedEventTextView = itemView.findViewById(R.id.loggedEvent_textview);
        }
    }
}
