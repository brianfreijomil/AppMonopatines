SCOOTER ENDPOINTS:

GET: http://localhost:8080/scooterApp/api/scooters/id
GET ALL: http://localhost:8080/scooterApp/api/scooters/
SAVE: http://localhost:8080/scooterApp/api/scooters
DELETE: http://localhost:8080/scooterApp/api/scooters/id
UPDATE: http://localhost:8080/scooterApp/api/scooters/id

EXAMPLE JSON SCOOTER

{
"licensePlate": "91218RP",
"available": true,
"ubication": {
"x": 3.1,
"y": 1.3
}
}
--------------------------------------------------------------------------------------
TRIP ENDPOINTS:

GET: http://localhost:8080/scooterApp/api/trips/id
GET ALL: http://localhost:8080/scooterApp/api/trips/
SAVE: http://localhost:8080/scooterApp/api/trips
DELETE: http://localhost:8080/scooterApp/api/trips/id
UPDATE: http://localhost:8080/scooterApp/api/trips/id

EXAMPLE JSON TRIP

{
"id": 37,
"initTime": "2023-10-27T15:19:00.000+00:00",
"endTime": "2023-10-27T21:20:00.000+00:00",
"kms": 12.84,
"ended": false
}