package com.business.command.handler;

import android.content.Context;
import android.content.Intent;
import com.business.callback.CommandCallback;
import com.business.command.bean.CommandMsg;
import activity.command.ToothActivity;
import activity.command.XjActivity;
import activity.command.ZytzActivity;

public class ActivityHandler extends CommandHandler{
    private Context context;

    public ActivityHandler(Context context){
        this.context = context;
    }

    @Override
    public void handleCommand(CommandMsg commandMsg, CommandCallback callback) {
        super.handleCommand(commandMsg, callback);
        if("1001".equals(commandMsg.name)){
            Intent intent = new Intent(context, ToothActivity.class);
            if(commandMsg.params != null)
                intent.putExtra("params", commandMsg.params);
            context.startActivity(intent);
        }else if("1002".equals(commandMsg.name)){
            context.startActivity(new Intent(context, ZytzActivity.class));
        }else if("1003".equals(commandMsg.name)){
            context.startActivity(new Intent(context, XjActivity.class));
        }

//        callback.onSuccess("linhao 999");
    }

}
