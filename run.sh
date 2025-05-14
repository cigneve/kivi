#! /usr/bin/env/ sh
if [ ! -f .env ]
then
  export $(cat .env | xargs)
fi

# My favorite from the comments. Thanks @richarddewit & others!
set -a && source .env && set +a
./mvnw clean spring-boot:run
