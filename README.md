# Description

  this is a app which fetches polls given from a web server and saves the results in a database, after computing the results the pie chart of some parts of the
  polls are shown in the poll activity part

# Prerequisites
This app uses server side that needs to be available in order for it to work. Server side is @all-polls-server repository.
Server main url address should be provided in PollsService.java.

```
private static final String baseUrl = "server_url";
```
this code is adapted from jasbubers all-polls-android project