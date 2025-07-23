'use client';

import { useEffect, useState } from 'react';
import { useAchievementApi } from '@/app/hooks/useAchievementApi';
import { PlayerAchievementDTO } from '@/src/generated/api';

interface PlayerAchievementsProps {
    pUUID: string;
}

export default function PlayerAchievements({ pUUID }: PlayerAchievementsProps) {
    const [achievements, setAchievements] = useState<PlayerAchievementDTO[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const achievementApi = useAchievementApi();

    useEffect(() => {
        const fetchPlayerAchievements = async () => {
            try {
                const { data } = await achievementApi.getPlayerAchievementByPUUID(pUUID, 'en_US');
                setAchievements(data);
            } catch (error) {
                console.error('Failed to fetch player achievements:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchPlayerAchievements();
    }, [pUUID]);

    if (isLoading) return <div>Loading achievements...</div>;

    return (
        <div>
            <h2>Player Achievements</h2>
            <div className="grid gap-4">
                {achievements.map((achievement) => (
                    <div key={achievement.challengeId} className="p-4 border rounded">
                        <h3>{achievement.level}</h3>
                    </div>
                ))}
            </div>
        </div>
    );
}
