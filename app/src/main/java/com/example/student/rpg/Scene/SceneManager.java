package com.example.student.rpg.Scene;

import com.example.student.rpg.ObjectManager;

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
                ObjectManager.ObjectClear(); // 現在のオブジェクトをすべて削除
                now_scene = new SceneTitle();
                break;

            case Game:
                ObjectManager.ObjectClear();  // 現在のオブジェクトをすべて削除
                now_scene = new SceneGame();
                break;
        }
    }
}
