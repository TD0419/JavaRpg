package com.example.student.rpg.Battle;

import com.example.student.rpg.Obj;

public class ObjBattle extends Obj
{
    public String m_name;

    // ステータス情報
    public class State_Info
    {
        public int hp;
        public int attack;
        public int speed;
    }

    // 敵に攻撃する際の情報
    public class Attack_info
    {
        public int enemy_number; // 何番目の敵に攻撃するか
    }

    public String GetName() { return m_name; }

    public Attack_info Attack() { return m_attack_info; }

    public void Defense(int attack)
    {
        // ダメージを与える
        m_state_info.hp -= attack;
    }

    //
    public State_Info GetState() { return m_state_info; }

    // ステータス情報
    protected State_Info m_state_info = new State_Info();
    protected Attack_info m_attack_info = new Attack_info();
}
