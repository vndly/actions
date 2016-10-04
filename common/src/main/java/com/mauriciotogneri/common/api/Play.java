package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class Play implements Serializable
{
    private final Action action;
    private final Integer volume;

    public Play(Action action, Integer volume)
    {
        this.action = action;
        this.volume = volume;
    }

    public Action action()
    {
        return action;
    }

    public float volume()
    {
        return (float)volume / 100f;
    }
}