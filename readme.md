# LoLchievement

LoLchievement is a web application that allows users to log into their Riot Games account and view a detailed analytical overview of their League of Legends achievements.

## 🔍 Overview

With LoLchievement, players can:
- Authenticate securely with their Riot account.
- View a personalized dashboard of their in-game achievements.
- Gain insight into progress across various champion roles, game modes, and seasons.
- Track achievement completion and see what’s left to earn.

## ✨ Features

- 🔐 Riot OAuth login
- 📊 Dynamic achievement visualization
- 🧠 Analytics for gameplay trends and progress
- ⚙️ Responsive and modern UI

## 🛠 Tech Stack

**Frontend**
- Next.js
- React
- TypeScript
- Tailwind CSS

**Backend**
- Spring Boot
- Java
- REST API integration with Riot's API

**Other Tools**
- PostgreSQL (or any SQL database)
- OAuth 2.0 for Riot authentication

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
 - Add user profile overview
 - Challenge overview
 - Arena champion progression overviez
 - Display match history highlights (arena)
 - Support for additional Riot games (e.g., TFT, LoR)

## 📄 License
This project is licensed under the MIT License.