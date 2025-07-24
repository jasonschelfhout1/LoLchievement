'use client';

import {useAchievementApi} from '@/app/hooks/useAchievementApi';
import {useAsyncCall} from '@/app/hooks/useAsyncCallHook';
import {AchievementDTO} from '@/src/generated/api';
import {useEffect, useState} from "react";
import {useParams} from "next/navigation";

export default function AchievementDetails() {
    const params = useParams();
    const achievementApi = useAchievementApi();
    const id = params.id as string;
    const {data: achievement, error, isLoading, execute} = useAsyncCall<AchievementDTO>();

    useEffect(() => {
        execute(achievementApi.getAchievementDetailByChallengeId(id, 'en_US'));
    }, [id]);

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;
    if (!achievement) return null;

    return (
        <div>
            <h1>{achievement.name}</h1>
            <img
                src={"http://localhost:8080/api/achievements/token/" + id}
                alt={`${achievement.name} token`}
                className="w-24 h-24 object-contain"
            />
            <p>{achievement.description}</p>
            <p>{achievement.shortDescription}</p>
            <p>{achievement.state}</p>
            {achievement.achievementThreshHolds?.map((threshold) => (
                <div key={`threshold-${threshold.value}`} className="mb-2">
                    {threshold.title && <p className="text-sm">{threshold.title}</p>}
                    <p>Value: {threshold.value}</p>
                </div>
            ))}
        </div>
    );
}
