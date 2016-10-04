package com.mauriciotogneri.actions;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mauriciotogneri.actions.ActionAdapter.ActionViewHolder;
import com.mauriciotogneri.actions.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.actions.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.api.Action;
import com.mauriciotogneri.common.api.Message;
import com.mauriciotogneri.common.api.MessageApi.Messages;
import com.mauriciotogneri.common.api.MessageApi.Paths;
import com.mauriciotogneri.common.api.Play;
import com.mauriciotogneri.common.utils.Serializer;

import java.util.List;

public class MainActivity extends Activity implements WearableEvents
{
    private String nodeId = "";
    private WearableConnectivity connectivity;
    private View progressBar;
    private WearableListView list;
    private View volumeLayer;
    private ActionAdapter adapter;
    private Action chosenAction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
                onLoad();
            }
        });
    }

    private void onLoad()
    {
        progressBar = findViewById(R.id.progress_bar);
        list = (WearableListView) findViewById(R.id.list);
        volumeLayer = findViewById(R.id.volume_layer);

        findViewById(R.id.volume_50).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (chosenAction != null)
                {
                    playAction(chosenAction, 50);
                }
            }
        });

        findViewById(R.id.volume_100).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (chosenAction != null)
                {
                    playAction(chosenAction, 100);
                }
            }
        });

        volumeLayer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);

        adapter = new ActionAdapter(this);

        list.setAdapter(adapter);
        list.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                volumeLayer.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);

                ActionViewHolder action = (ActionViewHolder) viewHolder;
                chosenAction = action.get();
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });

        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
    }

    private void playAction(Action action, int volume)
    {
        connectivity.sendMessage(Messages.playAction(nodeId, new Play(action, volume)));

        volumeLayer.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        chosenAction = null;
    }

    private void display(List<Action> actions)
    {
        progressBar.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);

        adapter.setData(actions);
    }

    @Override
    public void onConnectedSuccess()
    {
        connectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String deviceNodeId)
            {
                if (!TextUtils.isEmpty(deviceNodeId))
                {
                    nodeId = deviceNodeId;

                    connectivity.sendMessage(Messages.getActions(nodeId));
                }
                else
                {
                    displayToast(getString(R.string.loading_error));
                }
            }
        });
    }

    @Override
    public void onConnectedFail()
    {
        displayToast(getString(R.string.loading_error));
    }

    private void displayToast(final String message)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_ACTIONS))
        {
            List<Action> list = Serializer.deserialize(message.getPayload());
            display(list);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (connectivity != null)
        {
            connectivity.disconnect();
        }
    }
}