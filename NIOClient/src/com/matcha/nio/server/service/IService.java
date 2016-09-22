package com.matcha.nio.server.service;

import java.nio.channels.SelectionKey;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface IService
{
    void service(SelectionKey selectionKey);
}
