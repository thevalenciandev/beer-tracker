# beer-tracker
A simple RESTful API of beers and breweries.

[![Build Status](https://travis-ci.org/thevalenciandev/beer-tracker.png?branch=master)](https://travis-ci.org/thevalenciandev/beer-tracker)

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

5. GET http://localhost:8080/beers

```
{
  "_embedded": {
    "beers": [
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
      },
      {
        "name": "Life & Death",
        "style": "IPA",
        "abv": 6.5,
        "_links": {
          "self": {
            "href": "http://localhost:8080/beers/3"
          },
          "beers": {
            "href": "http://localhost:8080/beers"
          },
          "brewery": {
            "href": "http://localhost:8080/beers/3/brewery"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/beers"
    }
  }
}
```

6. GET http://localhost:8080/breweries

```
{
  "_embedded": {
    "breweries": [
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
      },
      {
        "name": "Vocation",
        "_links": {
          "self": {
            "href": "http://localhost:8080/breweries/4"
          },
          "breweries": {
            "href": "http://localhost:8080/breweries"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/breweries"
    }
  }
}
```
