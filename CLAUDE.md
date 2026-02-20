# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Online multiplayer Texas Hold'em poker app for private friend games with free virtual chips (no real money). Players create private tables and invite friends via shareable links or 6-character room codes. See `SPEC.md` for the full technical specification.

## Tech Stack

- **Frontend:** Flutter (Dart) — single codebase for iOS, Android, Web. State management with Riverpod.
- **Backend:** Java 21 + Spring Boot 3.x — REST APIs (`/api/v1/...`) + WebSocket server (STOMP over WebSocket).
- **Database:** PostgreSQL 16 (persistent data) + Redis 7 (active game state, ephemeral).
- **Auth:** Spring Security + JWT (access 15min / refresh 7d). OAuth2 for Google/Apple.
- **Message Schema:** [msgbuf](https://github.com/msgbuf/msgbuf) — proto3-like `.proto` definitions generate both Java and Dart data classes for game messages.
- **Deployment:** Docker Compose on a single self-hosted node with Caddy for TLS/reverse proxy.

## Architecture

### Game Engine (Pure Java)
The game engine is a **framework-independent pure Java library** — no Spring dependencies. It is the single source of truth for all game state mutations. This separation is intentional: it makes the engine independently testable and keeps game logic decoupled from infrastructure.

Key responsibilities: deck shuffling (`SecureRandom`), bet validation, side pot calculation, hand evaluation, turn timer, state serialization.

### Hand State Machine
`WAITING_FOR_PLAYERS → PRE_FLOP → FLOP → TURN → RIVER → SHOWDOWN → (loop)`

Any betting round can jump to SHOWDOWN if all but one player folds or all remaining players are all-in.

### Table Lifecycle
Tables are **session-based** — created on demand, destroyed when the last player leaves. No persistence or recovery across server restarts. Hand history is flushed to PostgreSQL before table cleanup.

### Game Messages (msgbuf)
Game message types (game state, player actions, hole cards, etc.) are defined in `.proto` schema files using msgbuf's proto3-extended syntax. The `msgbuf-generator-maven-plugin` generates Java classes for the backend; Dart classes are generated for the frontend. This ensures message types stay in sync across both codebases. When adding or modifying game messages, edit the `.proto` definitions — never hand-edit generated classes.

### Real-Time Communication
STOMP over WebSocket. Sensitive data (hole cards) sent only via user-destination queues (`/user/queue/cards`), never broadcast. Public state goes to `/topic/table/{tableId}/state`. Player actions sent to `/app/table/{tableId}/action`.

### Data Strategy
- PostgreSQL: users, hand history, friendships, OAuth identities (persistent)
- Redis: active game state, deck, seated players, session data, room code lookups (ephemeral)

## Build & Run Commands

```bash
# Backend
cd backend
./mvnw spring-boot:run              # Run Spring Boot app
./mvnw test                          # Run all backend tests
./mvnw test -Dtest=ClassName         # Run a single test class
./mvnw test -Dtest=ClassName#method  # Run a single test method

# Frontend
cd frontend
flutter run                          # Run Flutter app
flutter test                         # Run all Flutter tests
flutter test test/path/to_test.dart  # Run a single test file

# Full stack (Docker Compose)
docker compose up --build            # Start all services
docker compose down                  # Stop all services
```

## Testing Priorities

The game engine requires the highest test coverage (target ≥95%). Critical edge cases to always cover:
- Split pots and multiple side pots
- All-in with unequal stacks
- Disconnection mid-hand / timeout auto-fold
- Blind and dealer button rotation at table boundaries

Backend integration tests use Testcontainers for PostgreSQL/Redis. Flutter widget tests use Mocktail.

## Card Encoding

Two-character strings: rank + suit. Rank: `2-9, T, J, Q, K, A`. Suit: `h, d, c, s`. Example: `Ah` = Ace of Hearts, `Ts` = 10 of Spades.

## Key Design Constraints

- **Server-authoritative:** clients never receive cards they shouldn't see. All bet validations server-side.
- **9-max tables** (fixed). Private tables only — no public lobby or matchmaking.
- **Single node only** — no horizontal scaling or multi-node concerns for v1.
- **No real money** — no payment processing or regulatory compliance.
