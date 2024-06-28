#!/bin/bash

# Wait for SQL Server to start
sleep 15s

# Run the SQL commands
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P YourStrong@Passw0rd -d master -i /usr/src/app/setup.sql
