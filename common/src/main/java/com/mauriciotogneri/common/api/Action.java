package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class Action implements Serializable
{
    private final String name;
    private final String path;

    public Action(String name, String path)
    {
        this.name = name;
        this.path = path;
    }

    public String name()
    {
        return name;
    }

    public String path()
    {
        return path;
    }
}