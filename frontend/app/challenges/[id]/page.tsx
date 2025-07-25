'use client';

import {useChallengeApi} from '@/app/hooks/useChallengeApi';
import {useAsyncCall} from '@/app/hooks/useAsyncCallHook';
import {ChallengeDTO} from '@/src/generated/api';
import {useEffect, useState} from "react";
import {useParams} from "next/navigation";

export default function ChallengeDetails() {
    const params = useParams();
    const challengeApi = useChallengeApi();
    const id = params.id as string;
    const {data: challenge, error, isLoading, execute} = useAsyncCall<ChallengeDTO>();

    useEffect(() => {
        execute(challengeApi.getChallengeDetailByChallengeId(id, 'en_US'));
    }, [id]);

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;
    if (!challenge) return null;

    return (
        <div>
            <h1>{challenge.name}</h1>
            <img
                src={"http://localhost:8080/api/challenges/token/" + id}
                alt={`${challenge.name} token`}
                className="w-24 h-24 object-contain"
            />
            <p>{challenge.description}</p>
            <p>{challenge.shortDescription}</p>
            <p>{challenge.state}</p>
            {challenge.challengeThreshHolds?.map((threshold) => (
                <div key={`threshold-${threshold.value}`} className="mb-2">
                    {threshold.title && <p className="text-sm">{threshold.title}</p>}
                    <p>Value: {threshold.value}</p>
                </div>
            ))}
        </div>
    );
}
