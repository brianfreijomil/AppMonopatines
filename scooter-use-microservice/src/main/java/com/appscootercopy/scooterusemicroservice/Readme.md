# Metodos que necesitan info de scooter-use-microservice:

- deleteScooter(id) -> tiene que borrar todos los viajes asociados a esa scooter (o no, depende)
- findAllScooterByTripsAndYear() -> necesita los datos de trip-microservice (o este metodo puede ser solo un llamado al   
trip-microservice pero sobre la scooter solo tendriamos la matricula)
- findAllScooterTrip() -> lo elimine no tiene sentido que este en ese micro.
- findAllTripByLicensePlate(license) -> necesita datos de trip-microservice mediante un 
parametro, pero talvez no es necesario que exista en este microservicio.
- 