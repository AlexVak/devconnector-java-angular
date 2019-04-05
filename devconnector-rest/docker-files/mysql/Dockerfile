FROM mysql
ENV MYSQL_DATABASE devconnector
ENV MYSQL_USER devconnector
ENV MYSQL_PASSWORD devconnector
ENV MYSQL_ROOT_PASSWORD devconnector
COPY ./docker-files/mysql/schema-generation-mysql.sql /docker-entrypoint-initdb.d/
