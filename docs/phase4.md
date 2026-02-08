# Phase 4: Backend + Hardening + Offline

## Backend (Supabase + Postgres)
- Schema: `backend/schema.sql`
- Seed (optional): `backend/seed.sql`
- Auth: Supabase email/password, store role in `profiles.role`
- Wearables: Edge functions for Garmin/Polar OAuth
 - Android auth stub: `app/src/main/java/com/rockfit/ksa/data/remote/AuthRepository.kt`

## Production Hardening
- Analytics: Firebase Analytics (Android)
- Crash reporting: Firebase Crashlytics
- CI/CD: GitHub Actions build APK + lint (to be added)
- Release build: signed `release` APK once keystore is defined

## Offline + Sync
- WorkManager sync worker (stub): `app/src/main/java/com/rockfit/ksa/data/sync/SyncWorker.kt`
- Sync scheduler (periodic): `app/src/main/java/com/rockfit/ksa/data/sync/SyncScheduler.kt`
- Repository layer stub: `app/src/main/java/com/rockfit/ksa/data/repository/SyncRepository.kt`
- Local cache: DataStore JSON for athlete list (`AthleteStore`)
- Conflict strategy (current): Last-write-wins on remote (placeholder)
- Next: Room DB + field-level merge + server-side conflict APIs
