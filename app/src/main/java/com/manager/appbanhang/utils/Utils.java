package com.manager.appbanhang.utils;

import com.manager.appbanhang.model.GioHang;
import com.manager.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.0.100/banhang/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static String ID_RECEIVED;
    public static final String SENDID = "idSend";
    public static final String RECEIVEDID = "idReceived";
    public static final String MESS = "Message";
    public static final String DATETIME = "Datatime";
    public static final String PATH_CHAT = "chat";
}