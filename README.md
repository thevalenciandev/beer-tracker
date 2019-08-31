# beer-tracker
A simple RESTful API of beers and breweries.


1. GET http://localhost:8080/

```
{
  "_links": {
    "beers": {
      "href": "http://localhost:8080/beers"
    },
    "breweries": {
      "href": "http://localhost:8080/breweries"
    },
    "profile": {
      "href": "http://localhost:8080/profile"
    }
  }
}
```

2. PUT http://localhost:8080/beers

```
{
    "name": "Punk",
    "style": "IPA",
    "abv": 5.6,
    "brewery": {
    	"name": "BrewDog"
    }
}
```

=> 

```
{
  "name": "Punk",
  "style": "IPA",
  "abv": 5.6,
  "_links": {
    "self": {
      "href": "http://localhost:8080/beers/1"
    },
    "brewery": {
      "href": "http://localhost:8080/beers/1/brewery"
    },
    "beers": {
      "href": "http://localhost:8080/beers"
    }
  }
}
```

3. GET http://localhost:8080/beers/1

```
{
  "name": "Punk",
  "style": "IPA",
  "abv": 5.6,
  "_links": {
    "self": {
      "href": "http://localhost:8080/beers/1"
    },
    "beers": {
      "href": "http://localhost:8080/beers"
    },
    "brewery": {
      "href": "http://localhost:8080/beers/1/brewery"
    }
  }
}
```

4. GET http://localhost:8080/beers/1/brewery

```
{
  "name": "BrewDog",
  "_links": {
    "self": {
      "href": "http://localhost:8080/breweries/2"
    },
    "breweries": {
      "href": "http://localhost:8080/breweries"
    }
  }
}
```
