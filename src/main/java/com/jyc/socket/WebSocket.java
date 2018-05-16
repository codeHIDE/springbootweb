package com.jyc.socket;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyc.pojo.MessageVO;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {
 
  private Session session;
 
  private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
 
  private MessageVO messageVO = new MessageVO();
 
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSockets.add(this);
 
 
    messageVO.setType(1);
    messageVO.setUserNum(webSockets.size());
    messageVO.setMessage("有新的连接");
 
    ObjectMapper mapper = new ObjectMapper();
 
    String Json = "";
    try {
      Json = mapper.writeValueAsString(messageVO);
    } catch (Exception ex) {
    	System.out.println(ex.getMessage());
    }
 
    this.sendMessage(Json);
    System.out.println("【websocket消息】有新的连接, 总数:{}"+ webSockets.size());
  }
 
 
  @OnClose
  public void onClose() {
    webSockets.remove(this);
 
    messageVO.setType(2);
    messageVO.setUserNum(webSockets.size());
    messageVO.setMessage("有用户离开");
 
    ObjectMapper mapper = new ObjectMapper();
 
    String Json = "";
    try {
      Json = mapper.writeValueAsString(messageVO);
    } catch (Exception ex) {
    	System.out.println(ex.getMessage());
    }
 
    this.sendMessage(Json);
 
 
    System.out.println("【websocket消息】连接断开, 总数:{}"+webSockets.size());
  }
 
  @OnMessage
  public void onMessage(String message) {
 
    messageVO.setType(3);
    messageVO.setUserNum(webSockets.size());
    messageVO.setMessage(message);
 
    ObjectMapper mapper = new ObjectMapper();
 
    String Json = "";
    try {
      Json = mapper.writeValueAsString(messageVO);
    } catch (Exception ex) {
    	System.out.println(ex.getMessage());
    }
 
    this.sendMessage(Json);
 
    System.out.println("【websocket消息】收到客户端发来的消息:{}"+ message);
  }
 
  public void sendMessage(String message) {
    for (WebSocket webSocket : webSockets) {
    	System.out.println("【websocket消息】广播消息, message={}"+ message);
      try {
        webSocket.session.getBasicRemote().sendText(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}