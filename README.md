# Requirements:
1. Empty working Spring Boot App built with Maven
2. Has /hello-rest REST endpoint which returns ‘Hello World’ string
3. Has /hello endpoint which returns a HTML page with ‘Hello World’ string displayed
4. Has in-memory H2 database started with initial set of 10 different strings per language (‘Hello
World’ in 10 different languages) and /hello endpoints return the string determined by language
parameter passed inside the query
5. Has /secure/hello endpoint that requires user to log in with username and password
6. Has a secured ‘Admin’ page that allows the user to add new Language-Message pairs into the
database
7. Has an aspect (AOP) that performs logging for different endpoints with useful information
included into logs
8. Uses a standalone DB instead of in-memory H2 DB
9. Has ability to retrieve ‘Hello World’ translations from an external API (eg. Systran Translation
API)
10. Has ability to switch between DB and external API retrieval by using spring profiles
11. Use standalone DB instead of in-memory H2
12. Dockerize the application
