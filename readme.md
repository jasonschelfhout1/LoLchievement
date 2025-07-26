# LoLchievement

LoLchievement is a web application that allows users to log into their Riot Games account and view a detailed analytical
overview of their League of Legends challenges.

## 🔍 Overview

With LoLchievement, players can:

- Authenticate securely with their Riot account.
- View a personalized dashboard of their in-game challenges.
- Gain insight into progress across various champion roles, game modes, and seasons.
- Track challenge completion and see what’s left to earn.

## ✨ Features (to be implemented)

- 🔐 Riot OAuth login
- 📊 Dynamic challenge visualization
- 🧠 Analytics for gameplay trends and progress
- ⚙️ Responsive and modern UI

## 🛠 Tech Stack

**Frontend**

- Next.js
- React
- TypeScript
- Tailwind CSS
- HeroUI

**Backend**

- Spring Boot
- Java
- REST API integration with Riot's API

**Other Tools**

- PostgreSQL (or any SQL database)
- OAuth 2.0 for Riot authentication
- OpenApi

## 🚀 Getting Started

### Prerequisites

- Node.js & npm
- Java 17+
- PostgreSQL (or preferred database)
- Riot Developer API Key

### Frontend Setup

```bash
cd frontend
npm install
npm run generate-client
npm run dev
```

### Backend Setup

bash
Copy
Edit
cd backend
./mvnw spring-boot:run
Configure your application properties with the Riot API key and database credentials.

## 📌 Roadmap

1.Challenge overview

2. Add user profile overview

- Arena champion progression overviez
- Display match history highlights (arena)
- Support for additional Riot games (e.g., TFT, LoR)

## 🏁 Progression

- 25/07/25: (Frontend) Added HeroUI
- 23/07/25: Added Achievement & Player flow to OpenApi specs
- 21/07/25: (Backend) Implemented caching
- 21/07/25: (Frontend) OpenApi generates apiClient
- 21/07/25: (Backend) OpenApi generates api controller interfaces
- 21/07/25: Created OpenApi specs setup for generating DTO's & API calls
- 21/07/25: Created Riot API client connection
- 21/07/25: Init Java Spring Boot
- 21/07/25: Init Next.js

## 📄 License

This project is licensed under the MIT License.