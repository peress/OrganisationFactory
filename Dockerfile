FROM postgres:14.2-alpine

COPY /src/main/sql/create_schema.sql /docker-entrypoint-initdb.d/

EXPOSE 5432