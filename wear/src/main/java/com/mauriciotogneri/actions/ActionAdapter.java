package com.mauriciotogneri.actions;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.actions.ActionAdapter.ActionViewHolder;
import com.mauriciotogneri.common.api.Action;

public class ActionAdapter extends BaseAdapter<Action, ActionViewHolder>
{
    public ActionAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected synchronized void fill(Action action, ActionViewHolder viewHolder)
    {
        viewHolder.name.setText(action.name());
    }

    @Override
    protected ActionViewHolder getViewHolder(View view)
    {
        return new ActionViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_action;
    }

    public static class ActionViewHolder extends BaseViewHolder<Action>
    {
        public final TextView name;

        public ActionViewHolder(View itemView)
        {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}