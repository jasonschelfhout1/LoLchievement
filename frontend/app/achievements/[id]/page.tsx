'use client';

import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
import { useAchievementApi } from '@/app/hooks/useAchievementApi';
import { AchievementDTO } from '@/src/generated/api';

export default function AchievementPage() {
    const router = useRouter();
    const params = useParams();
    const [achievement, setAchievement] = useState<AchievementDTO | null>(null);
    const [error, setError] = useState<string | null>(null);
    const achievementApi = useAchievementApi();

    const id = params.id as string;

    useEffect(() => {
        const fetchAchievement = async () => {
            try {
                const { data } = await achievementApi.getAchievementDetailByChallengeId(id, 'en_US');
                console.log(data);
                setAchievement(data);
            } catch (err) {
                setError('Failed to load achievement');
                console.error(err);
            }
        };

        if (id) {
            fetchAchievement();
        }
    }, [id]);

    if (error) return <div>Error: {error}</div>;
    if (!achievement) return <div>Loading...</div>;

    return (
        <div>
            <button onClick={() => router.back()} className="mb-4">
                ← Back
            </button>
            <h1>{achievement.name}</h1>
            <p>{achievement.description}</p>
        </div>
    );
}