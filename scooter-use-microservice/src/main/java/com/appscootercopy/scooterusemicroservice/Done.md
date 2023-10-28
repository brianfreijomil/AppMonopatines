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
- DELETE BY deleteScooter()
- UPDATE (done)
- GET (done)
- GET ALL (done)

# ScooterTrip

- SAVE (process)
- DELETE (done) (delete by deleteScooter and deleteTrip)
- UPDATE (Not done)
- GET (Not Done)
- GET ALL (not done)

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
- GET (Not done)
- GET ALL (Not done)