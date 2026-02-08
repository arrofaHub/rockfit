# Rockfit Backend (Supabase + Postgres)

This folder contains the backend wiring plan and database schema for Phase 4.

Default stack:
- **Supabase** (Auth, Postgres, Edge Functions)
- **PostgreSQL** as the primary database
- **Firebase** for analytics + crash reporting (mobile only)

## Setup (Supabase)
1) Create a Supabase project.
2) In SQL editor, run `schema.sql`.
3) (Optional) Run `seed.sql` for demo data.
4) Copy project URL and anon key into the Android app once API client is wired.

## Auth Flow
- Supabase Auth (email/password).
- Roles stored in `profiles.role`.

## Wearables Server-side
Use Supabase Edge Functions to handle:
- Garmin OAuth exchange
- Polar OAuth exchange
- Token storage in `wearable_tokens`

## Env Vars (Edge Functions)
- SUPABASE_URL
- SUPABASE_ANON_KEY
- GARMIN_CLIENT_ID / GARMIN_CLIENT_SECRET
- POLAR_CLIENT_ID / POLAR_CLIENT_SECRET

