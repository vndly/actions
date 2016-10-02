package com.mauriciotogneri.common.api;

import com.mauriciotogneri.common.utils.Serializer;

import java.util.ArrayList;

public class MessageApi
{
    private MessageApi()
    {
    }

    public static final class Paths
    {
        public static final String GET_ACTIONS = "/get_actions";
        public static final String RESULT_ACTIONS = "/result_actions";
        public static final String RESULT_PLAY_ACTION = "/play_action";
    }

    public static final class Messages
    {
        public static Message getActions(String nodeId)
        {
            return new Message(nodeId, Paths.GET_ACTIONS);
        }

        public static Message resultActions(String nodeId, ArrayList<Action> elements)
        {
            return new Message(nodeId, Paths.RESULT_ACTIONS, Serializer.serialize(elements));
        }

        public static Message playAction(String nodeId, Action element)
        {
            return new Message(nodeId, Paths.RESULT_PLAY_ACTION, Serializer.serialize(element));
        }
    }
}