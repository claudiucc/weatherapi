# Weather API Proxy with Caching and Rate Limiting

## Overview

This project implements a caching proxy for an external weather API with per-user rate limiting. The proxy optimizes API usage, serves cached data quickly, and ensures users do not exceed their allowed request quota.

---

## Features

- **REST Endpoint:**  
  `GET /weather?city={city_name}`  

- **Caching:**  
  - Caches weather data for each city for **1 minute** to reduce external API calls.
  
- **City Search:**  
  - Uses **Nominatim API** to get city coordinates.
  - API URL: `https://nominatim.openstreetmap.org/search?q={city_name}&format=json`
  - Extracted fields:  
    - `lat` (latitude)  
    - `lon` (longitude)

- **Weather Data:**  
  - Uses **Open-Meteo API** to fetch current weather.  
  - API URL: `https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true`  
  - Returned fields:  
    - `temperature`  
    - `windspeed`  
    - `winddirection`

- **Rate Limiting:**  
  - Maximum **5 requests per minute per user**.
  - User ID passed via `USER_ID` header.
  - Exceeding limit returns **HTTP 429 Too Many Requests**.

- **Response Handling:**  
  - `200 OK` – Successful request  
  - `429 Too Many Requests` – Rate limit exceeded  
  - `500 Internal Server Error` – External API failure

---

## Implementation Details

### Caching
- **In-Memory Cache:**  
  - Options: `ConcurrentHashMap`, Guava Cache, or Caffeine.
  - Cached weather data expires after **1 minute**.

### Rate Limiting
- **Mechanisms:**  
  - Atomic counters with `ScheduledExecutorService`  
  - Token bucket / Leaky bucket (e.g., Guava `RateLimiter`)  

### Concurrency
- Thread-safe cache and rate limiter to handle **concurrent requests efficiently**.

### Performance
- Cached responses are served quickly to reduce latency.

### Scalability
- The design allows for **future distributed caching** (e.g., Redis).

### Resilience
- Graceful handling of external API failures.

---

## API Usage

### Request

```http
GET /weather?city=London
USER_ID: <user_id>
