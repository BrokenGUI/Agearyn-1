package me.voidless.core.functions;

import me.voidless.core.user.user.User;

public class Leveling {

    public static void addExperience(int experience, final User user){
        if (experience < 1) return;
        // Add experience to total experience
        user.statistics.totalExperience = user.statistics.totalExperience + experience;

        // Create and update some values
        int level = user.statistics.level;
        int cost = experienceForLevel(level + 1);
        experience = experience + user.statistics.experience;

        // Loop through
        while (experience >= cost){
            experience = experience - cost;
            level = level + 1;
            cost = experienceForLevel(level + 1);
        }

        if (level == user.statistics.level) user.statistics.experience = experience;
        else {
            user.statistics.level = level;
            user.statistics.experience = experience;

            // Call level up code
        }
    }

    public static int totalExperienceForLevel(final int level){
        int at = 1;
        int total = increasement(at);

        while (at < level){
            at++;
            total = total + increasement(at);
        }

        return total;
    }

    public static int experienceForLevel(final int level){
        return increasement(level);
    }

    private static int increasement(final int level){
        if (level < 10) return level * 30;
        else if (level < 30) return level * 50;
        else if (level < 50) return level * 70;
        else if (level <= 75) return level * 100;
        else return level * 125;
    }
}
