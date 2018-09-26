create keyspace IF NOT EXISTS user_service with replication={'class':'SimpleStrategy', 'replication_factor':3};
use user_service;
CREATE TABLE IF NOT EXISTS user(id UUID, email text PRIMARY KEY, password text, active boolean, role text, enable boolean);
CREATE TABLE IF NOT EXISTS token_jwt(id UUID PRIMARY KEY, user_id UUID, timestamp date);