'use client';

import { useEffect, useState } from 'react';
import { useChallengeApi } from '@/app/hooks/useChallengeApi';
import { PlayerChallengeDTO } from '@/src/generated/api';

interface PlayerChallengesProps {
    pUUID: string;
}

export default function PlayerChallenges({ pUUID }: PlayerChallengesProps) {
    const [challenges, setChallenges] = useState<PlayerChallengeDTO[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const challengeApi = useChallengeApi();

    useEffect(() => {
        const fetchPlayerChallenges = async () => {
            try {
                const { data } = await challengeApi.getPlayerChallengeByPUUID(pUUID, 'en_US');
                setChallenges(data);
            } catch (error) {
                console.error('Failed to fetch player challenges:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchPlayerChallenges();
    }, [pUUID]);

    if (isLoading) return <div>Loading challenges...</div>;

    return (
        <div>
            <h2>Player Challenges</h2>
            <div className="grid gap-4">
                {challenges.map((challenge) => (
                    <div key={challenge.challengeId} className="p-4 border rounded">
                        <h3>{challenge.level}</h3>
                    </div>
                ))}
            </div>
        </div>
    );
}
