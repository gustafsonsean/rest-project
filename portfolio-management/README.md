# Running

- Import project as maven project.
- update url, username, password, and defaultSchema to match your environment in application.yaml
  - Uses MySQL.
  - You will need to create a new database as well creating a sql user that has permissions on database.
- Run application.
- The api endpoint is set to http://localhost:9000/App/v1/...
- Note: you may create a new advisor by posting a request at http://localhost:9000/App/v1/advisor
  - The advisorId will be returned in the Location header.
