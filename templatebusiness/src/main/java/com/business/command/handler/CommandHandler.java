package com.business.command.handler;

import com.business.callback.CommandCallback;
import com.business.command.bean.CommandMsg;

/**
 *  命令处理者
 */
public abstract class CommandHandler {
    public Object handleCommand(CommandMsg command){
        return null;
    }

    public void handleCommand(CommandMsg commandMsg, CommandCallback callback){

    }
}
