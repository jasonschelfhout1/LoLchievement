import {ChallengeApi, Configuration} from '@/src/generated/api';

const configuration = new Configuration({
    basePath: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080',
    baseOptions: {
        headers: {
            'Content-Type': 'application/json',
        },
    }
});

export const useChallengeApi = () => {
    return new ChallengeApi(configuration);
};
