create keyspace daily1 with replication={'class':'SimpleStrategy', 'replication_factor':1};
use daily1;
CREATE TABLE user(id UUID, email text, password text, active boolean, roles list<text>, enabled boolean, user_id UUID, name TEXT, last_name TEXT, city TEXT, about TEXT, PRIMARY KEY(id, email));
CREATE TABLE token_jwt(id UUID PRIMARY KEY, user_id UUID, timestamp date);
CREATE TABLE mail_verification_token(id UUID PRIMARY KEY, user_id UUID, expire_date text);
CREATE TABLE oauth_preserved_state(id UUID PRIMARY KEY, state_key text, preservedState text);