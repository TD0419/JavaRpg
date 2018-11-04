package com.example.student.rpg.Scene;

// シーンを管理するクラス
public class SceneManager
{
    static Scene now_scene; // 現在のシーン

    // シーンの種類
    public enum Scene_Kind
    {
        Title,
        Game,
    }

    // シーン切り替え
    public static final void ChangeScene(Scene_Kind next_scene_kind)
    {
        switch (next_scene_kind)
        {
            case Title:
                now_scene = new SceneTitle();
                break;
        }
    }
}
