# Tests

---SCOOTER
- obtener una scooter por patente (devuelve scooter por id)
- obtener todas las scooters (devuelve todas las scooter)
- Obtener una scooter por patente no existente (devuelve NotFoundException)
- 
- alta de scooter (devuelve patente al crear)
- alta de scooter con patente ya existente (devuelve conflictException)
- verificado alta automatico de la ubicacion en la tabla Ubication


- modificacion de scooter (devuelve id al modificar)
- modificacion de scooter no existente (devuelve notFoundException)
- modificacion de scooter (cambiando patente) (devuelve conflictExecption)
- verificado modificacion automatica de ubicacion en la tabla Ubication


- baja de scooter (devuelve id al eliminar)
- baja de scooter no existente (devuelve NotFounException)
- verificado baja de todos los registros referenciados en tabla scooter_trip y viajes en tabla Trip


---TRIP
- obtener trip (devuelve trip por id)
- obtener todos los trip (devuelve todos los trip)
- obtener trip no existente (devuelve notFoundException)
- 
- alta de trip (devuelve el id de trip)
- alta de trip con id ya existente (devuelve conflictException)
- 
- modificacion de trip (devuelve id al modificar)
- modificacion de un trip no existente (devuelve notfoundexception)
- verificacion que al pasar por request un id de otro trip existente devuelva ConflictExistException
- 
- baja de trip (devuelve id al eliminar)
- baja de trip no existente (devuelve NotFoundException)
- verificacion de baja automatica de registro asociado en tabla ScooterTrip

---SCOOTERSTOP
- obtener scooter stop (devuelve por id de ubicacion)
- obtener todos los scooter stop (devuelve todas)
- obtener scooter stop no existente (devuelve notFoundException)
- 
- alta scooter stop (devuelve longitud y latitud de la parada al crear)
- alta de un scooterStop ya existente (devuelve ConflicExistExcepion)


- modificacion de un scooterStop (devuelve id de ubicacion)
- verificado que al querer modificar un scooterStop con los valores de otro scooterStop ya existente (devuelve ConflictExistException)
- modificacion de un scooterStop no existente (devuelve NotFoundException)
-
- baja de un scooterStop (anda RETESTEAR)


---UBICATION
- obtener ubication (devuelve por id)
- obtener todas las ubications (devuelve todas las ubicationes)
- obtener ubication inexistente (devuelve notFoundExcepion)

---SCOOTERTRIP
- obtener registro viaje de una scooter (devuelve registro)
- obtener todas los registros de viaje de scooters (devuelve todos los viajes registrados)
- obtener un scootertrip no existente (devuelve notFoundException)
- obtener un scootertrip con scooter no existente (devuelve NotFoundException)
- obtener un scootertrip con trip no existente (devuelve notFoundException)
- obtener todos los scootertrip de una scooter especifica (devuelve todos los viajes asociados de la scooter)
- obtener scootertrip de un trip especifico (devuelve registro)

- alta de scootertrip (devuelve id)
- alta de scootertrip ya existente (devuelve conflictExist)
- alta de scootertrip con idScooter no existente(devuelve notFoundexception)
- alta de scootertrip con idTrip no existente(devuelve notFoundexception)
- alta de scootertrip con ScooterTripId no existente(devuelve notFoundexception)

- las bajas estan determinadas por la baja de un trip o una scooter
- si elimino una scooter se eliminan todos los trip y scootertrip asociados

