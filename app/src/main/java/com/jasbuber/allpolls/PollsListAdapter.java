package com.jasbuber.allpolls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasbuber.allpolls.models.Poll;

import java.util.List;

public class PollsListAdapter extends RecyclerView.Adapter<PollsListAdapter.ViewHolder> {

    private final List<Poll> polls;
    private final OnListFragmentInteractionListener mListener;

    public PollsListAdapter(List<Poll> polls, OnListFragmentInteractionListener listener) {
        this.polls = polls;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_polls_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.poll = polls.get(position);
        holder.mIdView.setText(holder.poll.getTopic());
        holder.locationView.setText(holder.poll.getLocation());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.poll);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView locationView;
        public Poll poll;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            locationView = (TextView) view.findViewById(R.id.poll_location);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }

    public List<Poll> getPolls() {
        return polls;
    }
}
