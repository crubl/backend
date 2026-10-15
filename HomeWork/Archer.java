import java.util.Random;

public class Archer implements Units
{
    private int maxHealth = 75;
    private int health = maxHealth;
    private int attack = 15;
    private int critAttackUse = 3;

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
        return "Archer";
    }

    public int useCritAttack()
    {
        if (critAttackUse > 0)
        {
            int useOrNot = new Random().nextInt(0, 2);
            if (useOrNot == 1)
            {
                critAttackUse--;
                return attack * 3;
            }
        }
        return 0;
    }
}