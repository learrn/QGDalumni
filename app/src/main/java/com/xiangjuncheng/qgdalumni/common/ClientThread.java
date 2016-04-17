//package com.xiangjuncheng.qgdalumni.common;
//
//import android.content.Context;
//import android.content.Intent;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.xiangjuncheng.qgdalumni.control.activty.ChatActivity;
//import com.xiangjuncheng.qgdalumni.control.activty.ForumActivity;
//import com.xiangjuncheng.qgdalumni.control.activty.ShowForumPage;
//import com.xiangjuncheng.qgdalumni.control.fragment.GroupFragment;
//import com.xiangjuncheng.qgdalumni.model.bean.ChatEntity;
//import com.xiangjuncheng.qgdalumni.model.bean.ForumEntity;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.Socket;
//import java.util.List;
//
///**
// * Created by xiangjuncheng on 2016/1/26.
// */
//public class ClientThread extends Thread {
//    Context context;
//    Socket socket;
//
//    public ClientThread(Socket socket, Context context) {
//        this.socket = socket;
//        this.context = context;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            ObjectInputStream ois = null;
//            QGDMessage message;
//            Gson gson = new Gson();
//            try {
//                ois = new ObjectInputStream(socket.getInputStream());
//                message = (QGDMessage) ois.readObject();
//                switch (message.getType()) {
//                    case QGDMessage.COM_MES:
//                        Intent intent = new Intent("org.xjc.qgd.mes");
//                        String mMessage = gson.toJson(message);
//                        intent.putExtra("message", mMessage);
//                        intent.putExtra("type",message.getType());
//                        context.sendBroadcast(intent);
//                        break;
//                    case QGDMessage.RET_CHAT:
//                        //更新聊天记录
//                        Intent intent_chat = new Intent("org.xjc.qgd.mes");
//                        intent_chat.putExtra("type",message.getType());
//                        ChatActivity.chatEntityList = gson.fromJson(message.getContent(),new TypeToken<List<ChatEntity>>(){}.getType());
//                        context.sendBroadcast(intent_chat);
//                        break;
//                    case QGDMessage.RET_FORUM:
//                        //更新论坛信息
//                        Intent intent_forum = new Intent("org.xjc.qgd.mes");
//                        intent_forum.putExtra("type",message.getType());
//                        ForumActivity.forumEntities = gson.fromJson(message.getContent(),new TypeToken<List<ForumEntity>>(){}.getType());
//                        context.sendBroadcast(intent_forum);
//                    case QGDMessage.RET_FORUM_REC:
//                        //更新论坛回复
//                        Intent intent_forum_rec = new Intent("org.xjc.qgd.mes");
//                        intent_forum_rec.putExtra("type",message.getType());
//                        ShowForumPage.forumEntities = gson.fromJson(message.getContent(),new TypeToken<List<ForumEntity>>(){}.getType());
//                        context.sendBroadcast(intent_forum_rec);
//                }
//            } catch (Exception e) {
//                //e.printStackTrace();
//                try {
//                    if (socket != null) {
//                        socket.close();
//                    }
//                } catch (IOException e1) {
//                    //e1.printStackTrace();
//                }
//            }
//        }
//    }
//    public Socket getS(){
//        return socket;
//    }
//}
