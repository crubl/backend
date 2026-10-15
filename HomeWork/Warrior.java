import java.util.Random;

public class Warrior implements Units
{
    private int maxHealth = 100;
    private int health = maxHealth;
    private int attack = 10;
    private int specialAttackUse = 2;

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
        return "Warrior";
    }

    public int useSuperAttack()
    {
        if (specialAttackUse > 0)
        {
            int useOrNot = new Random().nextInt(0, 2);
            if (useOrNot == 1)
            {
                specialAttackUse--;
                return attack * 2;
            }
        }
        return 0;
    }
}