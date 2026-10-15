import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

interface Units
{
    int getMaxHealth();
    int getHealth();
    void getDamage(int damage);
    void heal(int amount);
    int getAttack();
    boolean isAlive();
    String getName();
}

public class Game
{

    public Units[] creatUnits(String teemName)
    {
        System.out.println("\nСоздание отряда: " + teemName);
        System.out.print("Из скольки человек состоит отряд (минимум 3): ");
        Scanner in = new Scanner(System.in);
        int countPeople = in.nextInt();

        if (countPeople < 3)
        {
            countPeople = 3;
            System.out.println("Количество человек увеличено до 3");
        }

        Units[] units = new Units[countPeople];

        for (int i = 0; i < countPeople; i++)
        {
            System.out.print("Выберите класс для бойца " + (i + 1) + ": \n1 - Warrior \n2 - Archer \n3 - Healer\n");
            int unit = in.nextInt();
            switch (unit)
            {
                case 1:
                    units[i] = new Warrior();
                    break;
                case 2:
                    units[i] = new Archer();
                    break;
                case 3:
                    units[i] = new Healer();
                    break;
                default:
                    System.out.println("Неверный выбор, по умолчанию выбран Warrior.");
                    units[i] = new Warrior();
                    break;
            }
        }
        return units;
    }

    public void startGame()
    {
        Units[] teem1 = creatUnits("Казбич");
        Units[] teem2 = creatUnits("Злыдни");

        System.out.println("\n_____Начало сражения!_____");

        int round = 1;
        while (hasAlive(teem1) && hasAlive(teem2) && round <= 15)
        {
            System.out.println("\n_____Раунд " + round + "_____");

            for (Units unit : teem1)
            {
                if (unit.isAlive() && hasAlive(teem2))
                {
                    Units target = getRandomAlive(teem2);
                    doTurn(unit, target, teem1);
                }
            }

            if (hasAlive(teem1) && hasAlive(teem2))
            {
                for (Units unit : teem2)
                {
                    if (unit.isAlive() && hasAlive(teem1))
                    {
                        Units target = getRandomAlive(teem1);
                        doTurn(unit, target, teem2);
                    }
                }
            }
            round++;
        }

        System.out.println("\n_____Конец сражения_____");
        if (hasAlive(teem1) && !hasAlive(teem2))
        {
            System.out.println("Победил Отряд 1!");
        }
        else if (!hasAlive(teem1) && hasAlive(teem2))
        {
            System.out.println("Победил Отряд 2!");
        }
        else
        {
            System.out.println("Ничья!");
        }
    }

    private void doTurn(Units attacker, Units target, Units[] ownSquad)
    {
        if (target == null || !target.isAlive()) return;

        int damage = 0;
        boolean specialUsed = false;

        if (attacker instanceof Warrior)
        {
            Warrior w = (Warrior) attacker;
            damage = w.useSuperAttack();
            if (damage > 0)
            {
                specialUsed = true;
                System.out.println("Warrior использует супер атаку!");
            }
            else
            {
                damage = w.getAttack();
            }
        }
        else if (attacker instanceof Archer)
        {
            Archer a = (Archer) attacker;
            damage = a.useCritAttack();
            if (damage > 0)
            {
                specialUsed = true;
                System.out.println("Archer использует критический выстрел!");
            }
            else
            {
                damage = a.getAttack();
            }
        }
        else if (attacker instanceof Healer)
        {
            Healer h = (Healer) attacker;

            Units lowestHealthAlly = getLowestHealthAlly(ownSquad);

            if (lowestHealthAlly != null && lowestHealthAlly.getHealth() < lowestHealthAlly.getMaxHealth())
            {
                int healAmount = h.useHeal(lowestHealthAlly);
                if (healAmount > 0)
                {
                    specialUsed = true;
                    System.out.println("Healer использует лечение на " + lowestHealthAlly.getName() + "! Восстановлено " + healAmount + " HP.");
                    System.out.println("HP " + lowestHealthAlly.getName() + ": " + lowestHealthAlly.getHealth() + "/" + lowestHealthAlly.getMaxHealth());
                }
                else
                {
                    damage = h.getAttack(); // Если не повезло с шансом, атакует
                }
            }
            else
            {
                damage = h.getAttack(); // Если все здоровы, атакует врага
            }
        }

        if (damage > 0)
        {
            target.getDamage(damage);
            if (specialUsed)
            {
                System.out.println(attacker.getName() + " наносит особой атакой " + damage + " урона цели " + target.getName() + "!");
            }
            else
            {
                System.out.println(attacker.getName() + " атакует " + target.getName() + " и наносит " + damage + " урона.");
            }
            System.out.println("HP " + target.getName() + ": " + target.getHealth() + "/" + target.getMaxHealth());

            if (!target.isAlive())
            {
                System.out.println(target.getName() + " погибает!");
            }
        }
    }

    private Units getLowestHealthAlly(Units[] squad)
    {
        Units lowest = null;
        int minHealth = 100000000;

        for (Units unit : squad)
        {
            if (unit.isAlive() && unit.getHealth() < minHealth)
            {
                minHealth = unit.getHealth();
                lowest = unit;
            }
        }
        return lowest;
    }

    private boolean hasAlive(Units[] squad)
    {
        for (Units unit : squad)
        {
            if (unit.isAlive())
            {
                return true;
            }
        }
        return false;
    }

    private Units getRandomAlive(Units[] squad)
    {
        ArrayList<Units> aliveUnits = new ArrayList<>();
        for (Units unit : squad)
        {
            if (unit.isAlive())
            {
                aliveUnits.add(unit);
            }
        }
        if (aliveUnits.isEmpty())
        {
            return null;
        }
        Random random = new Random();
        return aliveUnits.get(random.nextInt(aliveUnits.size()));
    }
}