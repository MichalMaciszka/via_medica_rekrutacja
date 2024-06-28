# Use the official Microsoft SQL Server image from the Docker Hub
FROM mcr.microsoft.com/mssql/server:2019-latest

# Set environment variables
ENV ACCEPT_EULA=Y
ENV MSSQL_PID=Express

# Expose the SQL Server port
EXPOSE 1433

# Copy initialization script
COPY ./init-db.sh /usr/src/app/init-db.sh
COPY ./setup.sql /usr/src/app/setup.sql

# Run the initialization script to set up the database
# RUN chmod +x /usr/src/app/init-db.sh
CMD /bin/bash /usr/src/app/init-db.sh & /opt/mssql/bin/sqlservr
