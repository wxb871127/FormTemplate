package com.business.command.commands;

import com.business.callback.CommandCallback;
import com.business.command.bean.CommandMsg;

/**
 * 命令抽象类
 */
public abstract class ICommand {
    public Object handle(CommandMsg command){
        return null;
    }

    public void handle(CommandMsg command, CommandCallback callback){

    }
}
