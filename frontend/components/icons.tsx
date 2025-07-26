import * as React from "react";

import {IconSvgProps} from "@/types";

export const Logo: React.FC<IconSvgProps> = ({
                                                 size = 36,
                                                 width,
                                                 height,
                                                 ...props
                                             }) => (
    <svg
        fill="none"
        height={size || height}
        viewBox="0 0 32 32"
        width={size || width}
        {...props}
    >
        <path
            clipRule="evenodd"
            d="M17.6482 10.1305L15.8785 7.02583L7.02979 22.5499H10.5278L17.6482 10.1305ZM19.8798 14.0457L18.11 17.1983L19.394 19.4511H16.8453L15.1056 22.5499H24.7272L19.8798 14.0457Z"
            fill="currentColor"
            fillRule="evenodd"
        />
    </svg>
);

export const LoginIcon = (props: IconSvgProps) => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        focusable="false"
        viewBox="0 0 24 24"
        aria-hidden="true"
        role="presentation"
        width="1em"
        height="1em"
        {...props}
    >
        <path
            strokeLinecap="round"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinejoin="round"
            d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M12 9l-3 3m0 0 3 3m-3-3h12.75"/>
    </svg>

);

export const SearchIcon = (props: IconSvgProps) => (
    <svg
        aria-hidden="true"
        fill="none"
        focusable="false"
        height="1em"
        role="presentation"
        viewBox="0 0 24 24"
        width="1em"
        {...props}
    >
        <path
            d="M11.5 21C16.7467 21 21 16.7467 21 11.5C21 6.25329 16.7467 2 11.5 2C6.25329 2 2 6.25329 2 11.5C2 16.7467 6.25329 21 11.5 21Z"
            stroke="currentColor"
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
        />
        <path
            d="M22 22L20 20"
            stroke="currentColor"
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
        />
    </svg>
);
