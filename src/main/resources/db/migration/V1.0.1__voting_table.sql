CREATE TABLE voting(
  id SERIAL PRIMARY KEY UNIQUE NOT NULL ,
  name citext,
  description citext,
  creator_id INTEGER REFERENCES users(id)

);



CREATE TABLE variant(
                      id SERIAL PRIMARY KEY UNIQUE NOT NULL ,
                      name citext,
                      description citext,
                      voting_id INTEGER REFERENCES voting(id)
);


