package com.thevalenciandev.beertracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class BeerNotFoundException extends RuntimeException {
}
