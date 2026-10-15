import java.util.Random;

public class Healer implements Units
{
    private int maxHealth = 50;
    private int health = maxHealth;
    private int attack = 5;
    private int HealUse = 3;

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getHealth()
    {
        return health;
    }

    public void getDamage(int damage)
    {
        health -= damage;
    }

    public void heal(int amount)
    {
        health += amount;
        if (health > maxHealth)
        {
            health = maxHealth;
        }
    }

    public int getAttack()
    {
        return attack;
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    public String getName()
    {
        return "Healer";
    }

    // Изменено: теперь принимает цель для лечения и лечит на attack * 3
    public int useHeal(Units target)
    {
        if (HealUse > 0)
        {
            int useOrNot = new Random().nextInt(0, 2);
            if (useOrNot == 1)
            {
                HealUse--;
                int healAmount = attack * 3;
                target.heal(healAmount);
                return healAmount;
            }
        }
        return 0;
    }
}