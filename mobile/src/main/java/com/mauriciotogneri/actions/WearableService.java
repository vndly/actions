package com.mauriciotogneri.actions;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.mauriciotogneri.common.api.Action;
import com.mauriciotogneri.common.api.Message;
import com.mauriciotogneri.common.api.MessageApi.Messages;
import com.mauriciotogneri.common.api.MessageApi.Paths;
import com.mauriciotogneri.common.utils.Serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WearableService extends WearableListenerService
{
    private static final int TIMEOUT = 1000 * 10; // in milliseconds

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        byte[] data = messageEvent.getData();

        if (TextUtils.equals(path, Paths.GET_ACTIONS))
        {
            getActions(nodeId);
        }
        else if (TextUtils.equals(path, Paths.RESULT_PLAY_ACTION))
        {
            playAction(data);
        }
    }

    private void getActions(String nodeId)
    {
        ArrayList<Action> actions = new ArrayList<>();

        File folder = new File(Environment.getExternalStorageDirectory(), "actions");
        File[] files = folder.listFiles();

        if (files != null)
        {
            List<File> sortedFiles = Arrays.asList(files);
            Collections.sort(sortedFiles);

            for (File file : sortedFiles)
            {
                actions.add(new Action(name(file.getName()), file.getAbsolutePath()));
            }
        }

        reply(Messages.resultActions(nodeId, actions));
    }

    private String name(String text)
    {
        String withoutIndex = text.substring(2);
        int lastDot = withoutIndex.lastIndexOf(".");

        if (lastDot != -1)
        {
            return withoutIndex.substring(0, lastDot);
        }
        else
        {
            return withoutIndex;
        }
    }

    private void playAction(byte[] data)
    {
        Action action = Serializer.deserialize(data);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 2, AudioManager.FLAG_PLAY_SOUND);

        MediaPlayer mediaPlayer = new MediaPlayer();

        try
        {
            mediaPlayer.setDataSource(action.path());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void reply(final Message message)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                GoogleApiClient client = new GoogleApiClient.Builder(WearableService.this).addApi(Wearable.API).build();
                ConnectionResult connectionResult = client.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                if (connectionResult.isSuccess())
                {
                    Wearable.MessageApi.sendMessage(client, message.getNodeId(), message.getPath(), message.getPayload());
                }

                client.disconnect();
            }
        }).start();
    }
}