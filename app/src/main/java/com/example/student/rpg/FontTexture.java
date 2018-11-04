package com.example.student.rpg;

import android.graphics.Bitmap;

import java.util.Hashtable;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import static android.graphics.Color.WHITE;

public class FontTexture
{
    private static Map<String, Integer> m_string_Textures_map =
            new Hashtable<String, Integer>();

    // 文字列を表示する関数
    public static final void DrawString(GL10 gl, float x, float y, float width, float height, String text,
                                  float r, float g, float b, float a )
    {
        // 文字情報がなければ、終了
        if(text == null) return;

        for(int i = 0; i < text.length(); i++)
        {
            // 文字を一つずつ分ける
            String string_draw = String.valueOf(text.charAt(i));

            // マップにビットマップ情報が登録されている文字かチェック
            Integer string_texture_id = m_string_Textures_map.get(string_draw);
            if(string_texture_id == null)
            {
                // なければビットマップを作成してマップに追加
                Bitmap bitmap = GraphicUtil.createStrImage(string_draw, WHITE);
                int tex_id = GraphicUtil.loadTexture(Global.gl, Global.context.getResources(), bitmap, 0);
                m_string_Textures_map.put(string_draw, tex_id);

                // 今作った文字のテクスチャidを取得
                string_texture_id = tex_id;
            }

            // 文字描画
            GraphicUtil.drawTexture(gl, x, y, width, height, string_texture_id, r, g, b, a);

            // 文字が複数あると重なるので、そうならないようにずらす
            x += width;
       }
    }

    public static final void DrawString(GL10 gl, float x, float y, float width, float height, String text)
    {
        DrawString(gl, x, y, width, height, text, 1.f, 1.f, 1.f, 1.f);
    }

    // マップ情報をクリアする関数(glとcontextが更新されたときに使う)
    public static final void MapClear()
    {
        m_string_Textures_map.clear();
    }
}
