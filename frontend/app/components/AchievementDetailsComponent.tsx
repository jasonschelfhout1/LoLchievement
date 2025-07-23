'use client';

import { useAchievementApi } from '@/app/hooks/useAchievementApi';
import { useAsyncCall } from '@/app/hooks/useAsyncCallHook';
import { AchievementDTO } from '@/src/generated/api';
import { useEffect } from "react";

export default function AchievementDetails({ challengeId }: { challengeId: string }) {
    const achievementApi = useAchievementApi();
    const { data: achievement, error, isLoading, execute } = useAsyncCall<AchievementDTO>();

    useEffect(() => {
        execute(achievementApi.getAchievementDetailByChallengeId(challengeId, 'en'));
    }, [challengeId]);

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;
    if (!achievement) return null;

    return (
        <div>
            <h1>{achievement.name}</h1>
            <p>{achievement.description}</p>
        </div>
    );
}
