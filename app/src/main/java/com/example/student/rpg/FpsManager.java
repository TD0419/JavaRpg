package com.example.student.rpg;

// fps管理
public class FpsManager
{
    private static long m_fps_count_start_time = System.currentTimeMillis();
    private static int m_fps = 0; // 現在のfpsを記録する変数
    private static int m_frames_in_secnod = 0; // フレーム数カウント用

    // fps計測
    public static final int FpsMeasurement()
    {
        // 現在の時間を取得
        long now_time = System.currentTimeMillis();
        // 現在時間との差分を計算する
        long difference = now_time - m_fps_count_start_time;
        // 1秒経過していた場合は、フレーム数のカウント終了
        if(difference >= 1000)
        {
            m_fps = m_frames_in_secnod;
            m_frames_in_secnod = 0;
            m_fps_count_start_time = now_time;
        }

        m_frames_in_secnod++; // フレーム数をカウント
        // フレーム数
        return m_fps;
    }
}
