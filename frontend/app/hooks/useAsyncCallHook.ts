import { useState } from 'react';

interface AsyncCallState<T> {
    data: T | null;
    error: Error | null;
    isLoading: boolean;
}

export const useAsyncCall = <T>() => {
    const [state, setState] = useState<AsyncCallState<T>>({
        data: null,
        error: null,
        isLoading: false,
    });

    const execute = async (promise: Promise<{ data: T }>) => {
        setState({ data: null, error: null, isLoading: true });
        try {
            const response = await promise;
            setState({ data: response.data, error: null, isLoading: false });
            return response.data;
        } catch (error) {
            setState({ data: null, error: error as Error, isLoading: false });
            throw error;
        }
    };

    return { ...state, execute };
};
