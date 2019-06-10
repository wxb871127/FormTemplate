package com.business.command.commands;


import android.app.Activity;
import android.content.Context;

import com.business.callback.CommandCallback;
import com.business.command.bean.CommandMsg;
import com.business.command.handler.ActivityHandler;
import com.business.command.handler.CommandHandler;

/**
 * 启动Acitivity的命令
 */
public class ActivityCommand extends ICommand {
    private CommandHandler commandHandler;

    public ActivityCommand(Context context){
        commandHandler = new ActivityHandler(context);
    }

    @Override
    public void handle(CommandMsg command, CommandCallback callback) {
        super.handle(command, callback);
        commandHandler.handleCommand(command, callback);
    }
}
