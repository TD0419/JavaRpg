package com.example.student.rpg;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // フルスクリーン表示
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // タイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // デバッグモードであるかを判定
        try
        {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
            Global.is_debug_gable = (ApplicationInfo.FLAG_DEBUGGABLE ==
                    (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        MyRenderer renderer = new MyRenderer(this); // MyRendererの生成

        // GLSurfaceViewの生成
        GLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
        // GLSurfaceViewにMyRendererを適用
        glSurfaceView.setRenderer(renderer);

        setContentView(glSurfaceView); // ビューをGLSurfaceViewに指定
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // テクスチャをすべて削除する
        TextureManager.deleteAll(Global.gl);
    }
}
