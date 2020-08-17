package com.jasbuber.allpolls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jasbuber.allpolls.models.PartialPoll;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jasbuber on 13/06/2016.
 */
public class PartialPollsAdapter extends BaseAdapter {

    List<PartialPoll> polls;

    Context context;

    public PartialPollsAdapter(Context context, List<PartialPoll> polls) {
        this.polls = polls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return polls.size();
    }

    @Override
    public Object getItem(int position) {
        return polls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return polls.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.partial_polls_item, null);

            holder.pollsterView = (TextView) convertView.findViewById(R.id.partial_poll_pollster);

            holder.providerView = (TextView) convertView.findViewById(R.id.partial_poll_provider);

            holder.lastUpdatedView = (TextView) convertView.findViewById(R.id.partial_poll_last_updated);

            convertView.setTag(holder);

            convertView.setOnClickListener(getOnClickListener(convertView));


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PartialPoll poll = polls.get(position);
        holder.id = poll.getId();
        holder.poll = poll;
        holder.pollsterView.setText(poll.getPollster());
        holder.providerView.setText(poll.getProvider());
        holder.lastUpdatedView.setText(
                new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(poll.getLastUpdated()));

        return convertView;
    }

    public View.OnClickListener getOnClickListener(final View convertView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartialPoll poll = ((ViewHolder) convertView.getTag()).poll;
                ((PollActivity) context).displayPartialPoll(poll);
            }
        };
    }

    public class ViewHolder {
        long id;
        PartialPoll poll;
        TextView pollsterView;
        TextView providerView;
        TextView lastUpdatedView;
    }
}
