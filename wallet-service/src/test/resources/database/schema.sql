CREATE TABLE WALLET (
  ADDRESS VARCHAR(34) NOT NULL PRIMARY KEY,
  PUBLIC_KEY VARCHAR(66) NOT NULL,
  SECRET VARCHAR(29) NOT NULL,
  WORD VARCHAR(20) NOT NULL
);

CREATE INDEX index_on_vanity_word ON WALLET (WORD);