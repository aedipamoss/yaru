# yaru

Illustration of building an API service in Clojure.

## Usage

### 1. `$ lein run`

This will start the web server at localhost:3000

### 2. Create a thing

```
curl -X POST -H "Content-Type: application/json" -d '
{
	"thing": {
		"title": "bar",
		"color": "green",
		"priority": "low"
	}
}' http://localhost:3000/things
```

### 3. Fetch a thing

```
curl -X GET -H "Content-Type: application/json" http://localhost:3000/things/1
```

Should return back the following result:

```json
{
	"thing": {
		"title": "bar",
		"color": "green",
		"priority": "low"
	}
}
```


## Details

```
Thing: _Just do it._
  ex. banana, gohan, deploy to staging

  Attributes:
    Title: Description of the task at hand
    Color: Hex code to be displayed later
    Priority: High, Medium, or Low

  API:
    GET: "/things/:id": thing/get: ([id]) -> {thing}
	GET: "/things": thing/list: ([]) -> [{thing},...]
	POST: "/things": thing/post: ([thing]) -> {thing}
	PUT: "/things/:id": thing/put: ([id thing]) -> {thing}
	DELETE: "/things/:id": thing/delete: ([id]) -> {thing}
```

## License

Copyright © 2017 Ædipa Moss

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
