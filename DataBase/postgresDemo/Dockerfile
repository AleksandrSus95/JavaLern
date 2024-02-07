FROM postgres:15.2

ENV POSTGRES_USER demo
ENV POSTGRES_PASSWORD demo_password

COPY demo-small-20170815.sql .

EXPOSE 5432

# docker volume create postgres-example
# docker build -t testpg:1 -f Dockerfile .
# docker run -d -p 5432:5432 --name=test_base -v postgres-example:/var/lib/postgresql/data testpg:1
# docker exec -ti test_base /bin/sh
# psql -U demo -f demo-small-20170815.sql