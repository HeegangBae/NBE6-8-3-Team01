import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    reactStrictMode: true,
    async rewrites() {
        return [
            {
                source: '/api/:path*',
                destination: 'https://bookbook.fly.dev/api/:path*',
            },
        ];
    },
    images: {
        remotePatterns: [
            {
                protocol: 'https',
                hostname: 'bookbook.fly.dev',
                port: '',
                pathname: '/**', // ⭐ 모든 경로의 이미지를 허용하도록 수정
            },
        ],
    },
};

export default nextConfig;