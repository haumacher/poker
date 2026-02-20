# Online Poker Game — Technical Specification

**Version:** 1.0 · **Status:** Draft · **Last Updated:** February 2026

---

## 1. Executive Summary

This document specifies the design and implementation of an online multiplayer Texas Hold'em poker application. The system enables users to create private tables, invite friends via shareable links or room codes, and play real-time poker with free virtual chips — no real-money wagering.

The application is built with a **Flutter front-end** (iOS, Android, Web) and a **Java Spring Boot back-end**, self-hosted on a single compute node using Docker Compose. Real-time game state is communicated over WebSockets, while REST APIs handle authentication, table management, and user profiles.

### Key Decisions

| Decision | Choice | Implication |
|---|---|---|
| Poker variant | Texas Hold'em only | Single game engine, no variant abstraction needed |
| Currency model | Free virtual chips | No payment processing, no regulatory compliance |
| Platforms | Mobile (iOS/Android) + Web | Flutter single codebase, responsive layout |
| Table size | 9-max (fixed) | UI designed for up to 9 seats |
| Table visibility | Private only (invite friends) | No lobby, no matchmaking, no public discovery |
| Table lifecycle | Session-based | Table destroyed when all players leave; no persistence/recovery |
| Infrastructure | Self-hosted, single node | Docker Compose, no orchestration overhead |

---

## 2. Goals & Non-Goals

### Goals

- Provide a smooth, real-time multiplayer Texas Hold'em experience for friend groups
- Allow users to create private tables and invite friends via link, QR code, or room code
- Support cross-platform play (iOS, Android, Web) from a single Flutter codebase
- Ensure fair gameplay with server-authoritative game logic
- Run reliably on a single self-hosted compute node

### Non-Goals (v1)

- Real-money gambling or any payment processing
- Public table lobby or matchmaking with strangers
- Tournament or sit-and-go structures
- Poker variants beyond Texas Hold'em
- AI/bot opponents
- Voice or video chat
- Horizontal scaling or multi-node deployment

---

## 3. Tech Stack

| Layer | Technology | Notes |
|---|---|---|
| Front-End | Flutter (Dart) | Single codebase for iOS, Android, Web |
| Back-End | Java 21 + Spring Boot 3.x | REST APIs + WebSocket server |
| Real-Time | Spring WebSocket (STOMP) | Bidirectional game state sync |
| Database | PostgreSQL 16 | User data, game history, table configs |
| Cache | Redis 7 | Active game state, session data |
| Auth | Spring Security + JWT | OAuth2 social login + email/password |
| Deployment | Docker Compose | Single node, all services containerized |
| Reverse Proxy | Caddy or Nginx | TLS termination, WebSocket proxying |

---

## 4. User Stories

### 4.1 Authentication & Profiles

| ID | Story | Priority |
|---|---|---|
| US-01 | As a user, I can register with email/password or social login (Google, Apple) | P0 |
| US-02 | As a user, I can set a display name and avatar | P0 |
| US-03 | As a user, I can view my game history and statistics | P1 |
| US-04 | As a user, I receive a starting chip balance and can claim daily free chips | P0 |
| US-05 | As a user, I can manage my account settings and notification preferences | P1 |

### 4.2 Table Management

| ID | Story | Priority |
|---|---|---|
| US-10 | As a user, I can create a new table with custom settings (blinds, buy-in range) | P0 |
| US-11 | As a user, I can share a table invite via link, QR code, or 6-character room code | P0 |
| US-12 | As a user, I can join a table by entering a room code or clicking an invite link | P0 |
| US-13 | As a table creator, I can kick players and lock/unlock the table | P1 |
| US-14 | As a user, I can see a list of my active tables for quick rejoin | P1 |

### 4.3 Gameplay

| ID | Story | Priority |
|---|---|---|
| US-20 | As a player, I can perform standard actions: fold, check, call, raise, all-in | P0 |
| US-21 | As a player, I see real-time updates of community cards, pot, and player actions | P0 |
| US-22 | As a player, I am prompted to act within a configurable time limit (15–60s) | P0 |
| US-23 | As a player, I can sit out temporarily and return to the next hand | P1 |
| US-24 | As a player, I can view a hand history replay after each hand | P1 |
| US-25 | As a player, I can use preset bet buttons (1/3 pot, 1/2 pot, pot, all-in) | P0 |
| US-26 | As a spectator, I can observe a table without occupying a seat | P2 |

### 4.4 Social

| ID | Story | Priority |
|---|---|---|
| US-30 | As a player, I can send quick chat messages or emojis at the table | P1 |
| US-31 | As a user, I can add friends and see their online status | P1 |
| US-32 | As a user, I can send direct table invites to friends | P1 |
| US-33 | As a user, I can report or block abusive players | P1 |

---

## 5. System Architecture

### 5.1 Component Overview

```
┌─────────────────────────────────────────────────────┐
│                   Flutter Client                     │
│         (iOS / Android / Web — single codebase)      │
└──────────┬──────────────────────┬────────────────────┘
           │ REST (HTTPS)         │ WebSocket (WSS)
           ▼                      ▼
┌──────────────────────────────────────────────────────┐
│              Caddy / Nginx (Reverse Proxy)            │
│              TLS termination, WS upgrade              │
└──────────┬──────────────────────┬────────────────────┘
           │                      │
           ▼                      ▼
┌──────────────────────────────────────────────────────┐
│            Spring Boot Application                    │
│  ┌─────────────┐  ┌──────────────┐  ┌─────────────┐ │
│  │  REST API    │  │  WebSocket   │  │  Game Engine │ │
│  │  Controllers │  │  Server      │  │  (pure Java) │ │
│  └─────────────┘  └──────────────┘  └─────────────┘ │
└──────────┬──────────────────────┬────────────────────┘
           │                      │
     ┌─────▼─────┐         ┌─────▼─────┐
     │ PostgreSQL │         │   Redis    │
     │ (persist)  │         │  (active   │
     │            │         │   state)   │
     └────────────┘         └────────────┘
```

All services run on a **single host via Docker Compose**:

```yaml
# docker-compose.yml (simplified)
services:
  app:
    build: ./backend
    ports: ["8080:8080"]
    depends_on: [postgres, redis]
  postgres:
    image: postgres:16
    volumes: ["pgdata:/var/lib/postgresql/data"]
  redis:
    image: redis:7-alpine
  caddy:
    image: caddy:2
    ports: ["443:443", "80:80"]
    volumes: ["./Caddyfile:/etc/caddy/Caddyfile"]
volumes:
  pgdata:
```

### 5.2 Game Engine

The game engine is a **pure Java library** with no Spring or framework dependencies, making it independently testable. All game state mutations flow through the engine as the single source of truth.

**Responsibilities:**

- Deck shuffling using `java.security.SecureRandom`
- Card dealing to players and community board
- Bet validation (minimum raise, all-in calculations, side pots)
- Blind and dealer button rotation
- Hand evaluation and winner determination
- Pot calculation including split pots and side pots
- Turn timer management and auto-fold on timeout
- Game state serialization for WebSocket broadcast

**Hand State Machine:**

```
WAITING_FOR_PLAYERS
        │
        ▼ (≥2 players ready)
    PRE_FLOP ──────────────────────┐
        │                          │
        ▼                          │
      FLOP                         │
        │                          │
        ▼                          ▼
      TURN                     SHOWDOWN
        │                          │
        ▼                          │
      RIVER ───────────────────────┘
                                   │
                                   ▼
                          WAITING_FOR_PLAYERS
                          (or next PRE_FLOP)
```

Any betting round can jump directly to SHOWDOWN if all but one player folds, or if all remaining players are all-in.

### 5.3 Session-Based Table Lifecycle

Since tables are session-based, the lifecycle is straightforward:

1. **Created** — Creator sets table params, gets room code / invite link
2. **Active** — At least one player is seated; hands play when ≥2 players ready
3. **Destroyed** — When the last player leaves (or disconnects beyond timeout), the table is removed from Redis; hand history is flushed to PostgreSQL before cleanup

There is no table recovery on server restart. If the server restarts, active tables are lost. This is acceptable for a casual social game on a single node.

---

## 6. WebSocket Communication

Real-time communication uses **STOMP over WebSocket**. Sensitive data (hole cards) is sent only to the specific player via user-destination queues.

### Channel Structure

| Channel | Direction | Purpose |
|---|---|---|
| `/topic/table/{tableId}/state` | Server → All | Public game state (community cards, pot, actions, chips) |
| `/user/queue/cards` | Server → Player | Private hole cards for the individual player |
| `/user/queue/errors` | Server → Player | Error messages (invalid action, insufficient chips) |
| `/app/table/{tableId}/action` | Player → Server | Player actions (fold, check, call, raise amount) |
| `/topic/table/{tableId}/chat` | Bidirectional | Chat messages and emoji reactions |
| `/topic/table/{tableId}/players` | Server → All | Player join/leave/sit-out events |

### Message Payloads

**Game State Broadcast** (server → all clients):

```json
{
  "tableId": "abc-123",
  "handNumber": 42,
  "phase": "FLOP",
  "communityCards": ["Ah", "Ks", "7d"],
  "pot": 1500,
  "sidePots": [],
  "currentPlayerSeat": 3,
  "dealerSeat": 1,
  "players": [
    {
      "seat": 1,
      "displayName": "Alice",
      "avatarUrl": "...",
      "chips": 4800,
      "currentBet": 200,
      "status": "ACTIVE",
      "lastAction": "CALL"
    }
  ],
  "turnTimeRemaining": 25,
  "minRaise": 400
}
```

**Player Action** (client → server):

```json
{
  "action": "RAISE",
  "amount": 600
}
```

**Hole Cards** (server → individual player):

```json
{
  "handNumber": 42,
  "cards": ["Qh", "Qd"]
}
```

---

## 7. REST API

All endpoints are prefixed with `/api/v1`. Authentication required unless noted.

### 7.1 Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register with email/password (no auth required) |
| POST | `/auth/login` | Login, returns JWT access + refresh tokens (no auth required) |
| POST | `/auth/oauth/{provider}` | OAuth2 login — Google, Apple (no auth required) |
| POST | `/auth/refresh` | Refresh expired access token |
| POST | `/auth/logout` | Invalidate refresh token |

### 7.2 Users

| Method | Endpoint | Description |
|---|---|---|
| GET | `/users/me` | Get current user profile |
| PATCH | `/users/me` | Update display name, avatar |
| GET | `/users/me/stats` | Get gameplay statistics |
| POST | `/users/me/daily-chips` | Claim daily free chips |
| GET | `/users/me/friends` | List friends |
| POST | `/users/me/friends/{userId}` | Send friend request |
| DELETE | `/users/me/friends/{userId}` | Remove friend |

### 7.3 Tables

| Method | Endpoint | Description |
|---|---|---|
| POST | `/tables` | Create a new table |
| GET | `/tables/{id}` | Get table details and seated players |
| POST | `/tables/{id}/join` | Join a table (optional seat preference) |
| POST | `/tables/{id}/leave` | Leave table and cash out chips |
| POST | `/tables/join-by-code/{code}` | Join table by 6-character room code |
| DELETE | `/tables/{id}` | Close table (creator only) |
| PATCH | `/tables/{id}/settings` | Update table settings (creator only) |
| POST | `/tables/{id}/kick/{userId}` | Kick a player (creator only) |

### 7.4 Game History

| Method | Endpoint | Description |
|---|---|---|
| GET | `/tables/{id}/hands` | List hand history for a table (paginated) |
| GET | `/hands/{handId}` | Get detailed hand replay data |
| GET | `/users/me/hands` | Get personal hand history across all tables |

### 7.5 Create Table — Example

**Request:** `POST /api/v1/tables`

```json
{
  "name": "Friday Night Poker",
  "smallBlind": 10,
  "bigBlind": 20,
  "minBuyin": 500,
  "maxBuyin": 2000,
  "turnTimerSeconds": 30
}
```

**Response:** `201 Created`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Friday Night Poker",
  "roomCode": "PKR-X7M",
  "inviteLink": "https://poker.example.com/join/PKR-X7M",
  "qrCodeUrl": "https://poker.example.com/api/v1/tables/.../qr",
  "createdBy": {
    "id": "...",
    "displayName": "Alice"
  },
  "settings": {
    "smallBlind": 10,
    "bigBlind": 20,
    "minBuyin": 500,
    "maxBuyin": 2000,
    "maxPlayers": 9,
    "turnTimerSeconds": 30
  },
  "status": "WAITING",
  "createdAt": "2026-02-20T18:00:00Z"
}
```

---

## 8. Database Schema

PostgreSQL stores persistent data. Active game state lives in Redis and is **not** persisted across server restarts (acceptable for session-based tables). Completed hands are flushed to PostgreSQL for history.

### Core Tables

```sql
-- Users
CREATE TABLE users (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(255) UNIQUE NOT NULL,
    password_hash   VARCHAR(255),           -- null for OAuth-only users
    display_name    VARCHAR(50) NOT NULL,
    avatar_url      VARCHAR(512),
    chip_balance    BIGINT NOT NULL DEFAULT 10000,
    last_daily_claim DATE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- OAuth identities (one user may have multiple providers)
CREATE TABLE oauth_identities (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    provider    VARCHAR(20) NOT NULL,       -- 'google', 'apple'
    provider_id VARCHAR(255) NOT NULL,
    UNIQUE(provider, provider_id)
);

-- Hand history
CREATE TABLE hand_history (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    table_name      VARCHAR(100) NOT NULL,
    hand_number     INT NOT NULL,
    dealer_seat     INT NOT NULL,
    small_blind     INT NOT NULL,
    big_blind       INT NOT NULL,
    community_cards JSONB,                  -- ["Ah", "Ks", "7d", "2c", "Jh"]
    pot_total       INT NOT NULL,
    winners         JSONB NOT NULL,         -- [{"userId":"...","amount":1500,"hand":"Two Pair"}]
    player_count    INT NOT NULL,
    started_at      TIMESTAMPTZ NOT NULL,
    ended_at        TIMESTAMPTZ NOT NULL
);

-- Individual actions within a hand
CREATE TABLE hand_actions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    hand_id         UUID NOT NULL REFERENCES hand_history(id) ON DELETE CASCADE,
    user_id         UUID NOT NULL REFERENCES users(id),
    seat            INT NOT NULL,
    action_type     VARCHAR(20) NOT NULL,   -- FOLD, CHECK, CALL, RAISE, ALL_IN
    amount          INT,
    phase           VARCHAR(20) NOT NULL,   -- PRE_FLOP, FLOP, TURN, RIVER
    sequence_num    INT NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Hand participants (what cards they held)
CREATE TABLE hand_players (
    hand_id     UUID NOT NULL REFERENCES hand_history(id) ON DELETE CASCADE,
    user_id     UUID NOT NULL REFERENCES users(id),
    seat        INT NOT NULL,
    hole_cards  JSONB,                      -- ["Qh", "Qd"] — null if mucked
    buyin_chips INT NOT NULL,
    final_chips INT NOT NULL,
    PRIMARY KEY (hand_id, user_id)
);

-- Friendships
CREATE TABLE friendships (
    user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    friend_id   UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, ACCEPTED, BLOCKED
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, friend_id)
);

-- Indexes
CREATE INDEX idx_hand_actions_hand_id ON hand_actions(hand_id);
CREATE INDEX idx_hand_players_user_id ON hand_players(user_id);
CREATE INDEX idx_hand_history_ended_at ON hand_history(ended_at DESC);
CREATE INDEX idx_friendships_friend_id ON friendships(friend_id);
```

### Redis Keys (Active Game State)

| Key Pattern | Type | Contents | TTL |
|---|---|---|---|
| `table:{id}:state` | Hash | Full game state (JSON) | None (deleted on table close) |
| `table:{id}:deck` | List | Remaining cards in shuffled deck | Per hand |
| `table:{id}:players` | Hash | Seated player data | None |
| `session:{userId}` | String | JWT session metadata | 7 days |
| `room-code:{code}` | String | Table ID lookup | None |

---

## 9. Flutter UI Architecture

### 9.1 State Management

The application uses **Riverpod** for state management.

| Provider | Responsibility |
|---|---|
| `authProvider` | JWT tokens, current user, login/logout state |
| `tableProvider` | Table creation, join/leave, active table list |
| `gameProvider` | Real-time game state from WebSocket, player actions |
| `chatProvider` | Table chat messages, emoji reactions |
| `userProvider` | Profile, chip balance, statistics |

### 9.2 Screen Flow

```
Splash (/)
  ├── Login/Register (/auth)
  └── Home (/home)
        ├── Create Table (/create-table)
        ├── Join by Code (/join)
        ├── Friends (/friends)
        ├── Profile (/profile)
        └── Table View (/table/{id})
              └── Hand History (/table/{id}/history)
```

| Screen | Description |
|---|---|
| Splash | App init, auth check, route to login or home |
| Login / Register | Email/password + social login |
| Home | Create table button, join-by-code input, active tables, friends online |
| Create Table | Form: table name, blinds, buy-in range, turn timer |
| Join by Code | Enter 6-character room code or paste invite link |
| Table View | Main game screen — poker table, cards, actions, chat |
| Hand History | Scrollable list of completed hands with replay |
| Profile | Stats, avatar, settings, chip balance |
| Friends | Friend list, online status, invite to table |

### 9.3 Game Table UI — Widget Tree

The Table View is the most complex screen:

```
TableView
├── PokerTableCanvas         — Custom painter: green felt, card zones, pot area
├── PlayerSeat × 9           — Avatar, name, chips, cards, action indicator
│   └── TurnTimer            — Circular countdown on active player
├── CommunityCardsRow        — Animated flop/turn/river with flip effect
├── PotDisplay               — Chip total, side pot breakdown
├── DealerButton             — Animated chip moving between hands
├── ActionBar (bottom)       — Fold / Check / Call / Raise + slider
│   └── BetPresetsRow        — Quick buttons: 1/3 pot, 1/2 pot, pot, all-in
└── ChatOverlay              — Semi-transparent message area + emoji
```

### 9.4 Animations

| Animation | Description |
|---|---|
| Card deal | Cards slide from deck position to player seats with stagger delay |
| Card flip | 3D Y-axis rotation when community cards are revealed |
| Chip movement | Chip stacks animate from player to pot on bet, pot to winner on win |
| Action popup | Brief label ("Raise 600") fades above player seat |
| Win highlight | Winning hand cards pulse/glow, winner seat highlighted |
| Haptic feedback | Light on deal, medium on action, heavy on all-in |

---

## 10. Security

### Authentication & Authorization

- JWT access tokens (15 min) + refresh tokens (7 days)
- Refresh tokens in HttpOnly secure cookies (web) and secure storage (mobile)
- WebSocket authenticated via JWT on initial STOMP CONNECT frame
- All table actions validated server-side: only seated, active players can act; only creators can modify settings or kick

### Game Integrity

- **Server-authoritative:** clients never receive cards they shouldn't see
- Deck shuffled with `java.security.SecureRandom`
- All bet validations server-side; client inputs treated as untrusted
- Rate limiting on WebSocket actions (max 5 actions/second per player)
- Hand history is immutable for dispute resolution

### Data Protection

- TLS via Caddy (automatic Let's Encrypt)
- Passwords hashed with bcrypt (cost factor 12)
- GDPR-compliant: users can export and delete their data

---

## 11. Self-Hosted Deployment

### Hardware Requirements (Minimum)

| Resource | Recommended |
|---|---|
| CPU | 2 cores |
| RAM | 4 GB |
| Disk | 20 GB SSD |
| Network | 100 Mbps, static IP or DDNS |

This should comfortably support ~50 concurrent players / ~10 active tables. The bottleneck will be WebSocket connections and Redis memory.

### Docker Compose Services

| Service | Image / Build | Ports |
|---|---|---|
| `caddy` | caddy:2 | 80, 443 (public) |
| `app` | Custom (Spring Boot fat JAR) | 8080 (internal) |
| `postgres` | postgres:16-alpine | 5432 (internal) |
| `redis` | redis:7-alpine | 6379 (internal) |

### Backup Strategy

- PostgreSQL: daily `pg_dump` to local backup directory + optional offsite sync
- Redis: RDB snapshots (default); data is ephemeral by design so loss is acceptable
- Application logs: rotated daily, retained for 30 days

### Monitoring (Lightweight)

For a single-node setup, a full Prometheus/Grafana stack is overkill. Recommended:

- Spring Boot Actuator (`/actuator/health`, `/actuator/metrics`) for app health
- `docker stats` or **ctop** for container resource usage
- Simple uptime check via cron + curl to `/actuator/health`
- Log aggregation: `docker compose logs -f` or ship to a file with rotation

---

## 12. Testing Strategy

| Level | Scope | Tools |
|---|---|---|
| Unit | Game engine: hand evaluation, betting, pot splitting, edge cases | JUnit 5, Mockito |
| Integration | REST endpoints, WebSocket flows, database queries | Spring Boot Test, Testcontainers |
| Widget | Flutter UI components, state management | Flutter Test, Mocktail |
| E2E | Full flow: create table → join → play hand → win → leave | Flutter Integration Test |
| Load | WebSocket concurrency, concurrent tables | k6 or Gatling |

The game engine is the highest-priority test target with ≥95% coverage. Critical edge cases: split pots, multiple side pots, all-in with unequal stacks, disconnection mid-hand, simultaneous timeout.

---

## 13. Future Considerations (v2+)

- Tournament mode with blind level escalation
- Additional variants: Omaha, Short Deck
- AI opponents for solo practice
- Public lobby and matchmaking
- Voice chat via WebRTC
- Cosmetic shop: card backs, table themes, avatar items
- Leaderboards and seasonal rankings
- Spectator mode with delayed card reveals for streaming
- Persistent tables that survive across sessions
- Multi-node deployment if player base grows

---

## Appendix

### A. Card Encoding

Cards are two-character strings: rank + suit.

- **Rank:** 2–9, T (10), J, Q, K, A
- **Suit:** h (hearts), d (diamonds), c (clubs), s (spades)
- **Examples:** `Ah` = Ace of Hearts, `Ts` = 10 of Spades, `2c` = 2 of Clubs

### B. Poker Hand Rankings

| Rank | Hand | Example |
|---|---|---|
| 1 | Royal Flush | A♠ K♠ Q♠ J♠ T♠ |
| 2 | Straight Flush | 9♥ 8♥ 7♥ 6♥ 5♥ |
| 3 | Four of a Kind | K♠ K♥ K♦ K♣ 3♠ |
| 4 | Full House | J♠ J♥ J♦ 8♣ 8♠ |
| 5 | Flush | A♣ J♣ 8♣ 5♣ 2♣ |
| 6 | Straight | T♠ 9♥ 8♦ 7♣ 6♠ |
| 7 | Three of a Kind | Q♠ Q♥ Q♦ 9♣ 4♠ |
| 8 | Two Pair | A♠ A♥ 7♦ 7♣ K♠ |
| 9 | One Pair | T♠ T♥ A♦ 8♣ 3♠ |
| 10 | High Card | A♠ J♥ 9♦ 5♣ 2♠ |

### C. Error Codes

| Code | Message | Context |
|---|---|---|
| `TABLE_FULL` | This table has no available seats | Join table |
| `INSUFFICIENT_CHIPS` | Not enough chips for minimum buy-in | Join table |
| `INVALID_ACTION` | Action not valid in current game state | Game action |
| `NOT_YOUR_TURN` | It is not your turn to act | Game action |
| `RAISE_TOO_SMALL` | Raise must be at least {minRaise} chips | Game action |
| `TABLE_NOT_FOUND` | Table does not exist or has been closed | Join table |
| `ALREADY_SEATED` | You are already seated at this table | Join table |
| `AUTH_EXPIRED` | Session expired, please log in again | Any request |
| `ROOM_CODE_INVALID` | No table found for this room code | Join by code |
