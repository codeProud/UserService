create keyspace IF NOT EXISTS user_service with replication={'class':'SimpleStrategy', 'replication_factor':1};
use user_service;
CREATE TABLE IF NOT EXISTS user(id UUID, email text, password text, active boolean, roles list<text>, enabled boolean, user_id UUID, name TEXT, last_name TEXT, city TEXT, about TEXT, PRIMARY KEY(id, email));
CREATE TABLE IF NOT EXISTS token_jwt(id UUID PRIMARY KEY, user_id UUID, timestamp date);
CREATE TABLE IF NOT EXISTS mail_verification_token(id UUID PRIMARY KEY, user_id UUID, expire_date text);
CREATE TABLE IF NOT EXISTS oauth_preserved_state(id UUID PRIMARY KEY, state_key text, preserved_state text);