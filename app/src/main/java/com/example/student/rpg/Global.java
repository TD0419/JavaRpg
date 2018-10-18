package com.example.student.rpg;

import android.content.Context;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class Global
{
    // GLコンテキストを保持する変数
    public static GL10 gl;

    public static Context context;

    // ランダムな値を生成
    public static Random random = new Random(System.currentTimeMillis());

    public static float touch_x;
    public static float touch_y;
    public static boolean touch_push = false; // 画面を押した瞬間
    public static boolean touch_leave = false;// 画面を押しているとき

    public final static int message_window_res_id = 1000;
}
