package com.example.student.rpg.Battle;

import com.example.student.rpg.Obj;

public class ObjBattle extends Obj
{
    public String m_name;

    // キャラクターの立ち位置
    public enum Character_Kind
    {
        Ally(0), // 味方
        Enemy(1),// 敵
        ;

        private int id;

        Character_Kind(final int id)
        {
            this.id = id;
        }

        public int getInt()
        {
            return this.id;
        }
    }

    // 敵に攻撃する際の情報
    public class Attack_info
    {
        public int enemy_number; // 何番目の敵に攻撃するか
    }

    public String GetName() { return m_name; }
    public Attack_info GetAttack() { return m_attack_info; }

    public void Defense(int attack)
    {
        // ダメージを与える
        m_state_info.hp -= attack;
    }

    //
    public BattleStateInfo GetBattleState() { return m_state_info; }

    // ステータス情報
    protected BattleStateInfo m_state_info = new BattleStateInfo();
    protected Attack_info m_attack_info = new Attack_info();
}
