# CRUD

# Scooter

- SAVE (done)(auto save (ubication) caused by Cascade.PERSIST)
- DELETE (done)(delete scooter by Id and delete every row referenced in scooter_trip/trip) ((auto delete de ubication en tabla Ubication))
- UPDATE (done)(auto update (ubication))
- GET (done)
- GET ALL (done)

# Trip

- SAVE (done)
- DELETE (done)
- DELETE due deleteScooter()
- UPDATE (done)
- GET (done)
- GET ALL (done)

# ScooterTrip

- SAVE (done)
- DELETE (done) (delete due deleteScooter and deleteTrip)
- UPDATE (not done)
- GET (done)
- GET ALL (done)
- - GET ALL BY SCOOTER (done)
- - GET BY TRIP (done)

# ScooterStop

- SAVE (done) (auto save de ubication en tabla Ubication)
- DELETE (done) (auto delete de ubication en tabla Ubication)
- UPDATE (done) (auto update de ubication en tabla Ubication)
- GET (done)
- GET ALL (done)

# Ubication

- SAVE (done)
- DELETE (done)
- UPDATE (done)
- GET (done)
- GET ALL (done)