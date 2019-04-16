CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE IF NOT EXISTS users(
  id SERIAL NOT NULL PRIMARY KEY,
  email citext UNIQUE NOT NULL,
  public_key citext UNIQUE,
  password citext

);