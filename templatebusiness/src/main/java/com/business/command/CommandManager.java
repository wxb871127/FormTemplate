package com.business.command;

import android.content.Context;

import com.business.callback.CommandCallback;
import com.business.command.bean.CommandMsg;
import com.business.command.commands.ActivityCommand;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import static base.util.TemplateParse.getDocumentElement;


public class CommandManager {

    private static CommandManager instance = null;
    private static Context mContext;

    private CommandManager(){

    }

    public static CommandManager getInstance(Context context){
        mContext = context;
        if(instance == null){
            synchronized (CommandManager.class){
                if(instance == null)
                    instance = new CommandManager();
            }
        }
        return instance;
    }

    //同步处理命令
    public Object execute(CommandMsg command){
        //暂时省略类型判断 目前只有Activity跳转
       return new ActivityCommand(mContext).handle(command);
    }

    //异步处理命令
    public void execute(CommandMsg command, CommandCallback callback){
        new ActivityCommand(mContext).handle(command, callback);
    }

    public CommandMsg parseCommand(String commandId){
        CommandMsg msg = new CommandMsg();
        try {
            InputStream inputStream = null;
            inputStream = mContext.getAssets().open("template_business.xml");
            Element rootElement = getDocumentElement(inputStream);
            NodeList nodeList = rootElement.getElementsByTagName("command");

            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element) node;
                if(commandId.equals(element.getAttribute("name"))){
                    msg.name = commandId;
                    msg.showtext = element.getAttribute("showtext");
                    msg.type = element.getAttribute("type");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
