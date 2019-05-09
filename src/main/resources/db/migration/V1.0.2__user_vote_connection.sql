CREATE TABLE user_vote(
                     id SERIAL PRIMARY KEY UNIQUE NOT NULL ,
                     user_id INTEGER REFERENCES users(id),
                     vote_id INTEGER REFERENCES voting(id)

);