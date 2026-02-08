-- Rockfit Core Schema (PostgreSQL)

create table if not exists profiles (
  id uuid primary key,
  email text unique not null,
  full_name text not null,
  role text not null default 'athlete',
  created_at timestamptz default now()
);

create table if not exists athletes (
  id uuid primary key,
  profile_id uuid references profiles(id) on delete cascade,
  sport text,
  region text,
  birth_year int,
  height_cm int,
  weight_kg int,
  created_at timestamptz default now()
);

create table if not exists lifestyle (
  athlete_id uuid primary key references athletes(id) on delete cascade,
  avg_sleep_hours numeric,
  hydration_liters numeric,
  caffeine_mg int,
  nutrition_style text,
  training_sessions_per_week int,
  stress_level text,
  travel_days_per_month int,
  recent_injury_notes text
);

create table if not exists health_metrics (
  id uuid primary key,
  athlete_id uuid references athletes(id) on delete cascade,
  title text,
  value text,
  unit text,
  status text,
  captured_at timestamptz default now()
);

create table if not exists training_sessions (
  id uuid primary key,
  athlete_id uuid references athletes(id) on delete cascade,
  label text,
  load int,
  duration_minutes int,
  distance_km numeric,
  session_date date
);

create table if not exists injury_logs (
  id uuid primary key,
  athlete_id uuid references athletes(id) on delete cascade,
  title text,
  injury_date date,
  status text,
  expected_return date
);

create table if not exists ai_insights (
  id uuid primary key,
  athlete_id uuid references athletes(id) on delete cascade,
  title text,
  summary text,
  action text,
  category text,
  confidence int,
  created_at timestamptz default now()
);

create table if not exists wearable_tokens (
  id uuid primary key,
  athlete_id uuid references athletes(id) on delete cascade,
  provider text,
  access_token text,
  refresh_token text,
  expires_at timestamptz
);

