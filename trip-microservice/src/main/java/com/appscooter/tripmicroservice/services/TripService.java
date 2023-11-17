package com.appscooter.tripmicroservice.services;

import com.appscooter.tripmicroservice.config.utils.JWTUtill;
import com.appscooter.tripmicroservice.domain.GeneralPrice;
import com.appscooter.tripmicroservice.domain.PauseTrip;
import com.appscooter.tripmicroservice.domain.Trip;
import com.appscooter.tripmicroservice.repositories.GeneralPriceRepository;
import com.appscooter.tripmicroservice.repositories.TariffRepository;
import com.appscooter.tripmicroservice.repositories.TripRepository;
import com.appscooter.tripmicroservice.repositories.interfaces.ScootersByTripsAndYearInterface;
import com.appscooter.tripmicroservice.services.dtos.generalprice.request.GeneralPriceRequestDTO;
import com.appscooter.tripmicroservice.services.dtos.generalprice.response.GeneralPriceResponseDTO;
import com.appscooter.tripmicroservice.services.dtos.scooter.ScooterResponseDTO;
import com.appscooter.tripmicroservice.services.dtos.tariff.request.TotalProfitsRequestDTO;
import com.appscooter.tripmicroservice.services.dtos.tariff.response.ReportProfitsDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.requests.FinishTripRequestDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.requests.TripRequestDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.requests.TripsAndYearRequestDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.responses.ReportScootersDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.responses.ScooterByTripsYearResponseDTO;
import com.appscooter.tripmicroservice.services.dtos.trip.responses.TripResponseDTO;
import com.appscooter.tripmicroservice.services.exception.ConflictExistException;
import com.appscooter.tripmicroservice.services.exception.NotFoundException;
import com.appscooter.tripmicroservice.services.exception.PauseActiveException;
import com.appscooter.tripmicroservice.services.timer.TimerPause;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TripService")
public class TripService {

    private TripRepository tripRepository;
    private TariffRepository tariffRepository;
    private GeneralPriceRepository priceRepository;

    private JWTUtill jwtUtill;

    private WebClient.Builder webClient;

    @Autowired
    HttpServletRequest request;

    public TripService(TripRepository t, TariffRepository tariffRepository,
                       GeneralPriceRepository pr, JWTUtill jwtUtill) {
        this.jwtUtill = jwtUtill;
        this.tripRepository=t;
        this.tariffRepository=tariffRepository;
        this.priceRepository=pr;
    }

    @Transactional(readOnly = true)
    public TripResponseDTO findTripById(long id) {
        return tripRepository.findById(id)
                .map(TripResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Trip", "Id", id));
    }

    @Transactional(readOnly = true)
    public List<TripResponseDTO> findAllTrip() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(s1-> new TripResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TripResponseDTO> findAllTripByScooter(String licenseScooter) {
            List<Trip> trips = this.tripRepository.findAllByLicenseScooter(licenseScooter);
            return trips.stream().map(t-> new TripResponseDTO(t))
                    .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity saveTrip(TripRequestDTO request) {
        if(!this.tripRepository.existsById(request.getId())){
            String licensePlate = request.getLicenseScooter();
            String token = this.request.getHeader(HttpHeaders.AUTHORIZATION);
            ScooterResponseDTO scooter = this.webClient.build()
                    .get()
                    .uri("http://scooter-use-microservice/api/scooter/{licensePlate}", licensePlate)
                    .headers(httpHeaders -> {httpHeaders.set("Authorization", token);})
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ScooterResponseDTO.class)
                    .block();
            if(scooter!=null) {
                Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
                GeneralPrice currentPrices = this.priceRepository.findByCurrent(true);
                if(currentPrices != null) {
                    GeneralPrice lastPrices = this.priceRepository.findByLastDateValidity(currentPrices.getDateValidity());
                    if(lastPrices == null || currentDate.compareTo(lastPrices.getDateValidity()) < 0) {
                        if(this.jwtUtill.getUserName(token).equals(request.getUserEmail())) {
                            this.tripRepository.save(new Trip(request, currentPrices.getPriceService()));
                            return new ResponseEntity(request.getId(), HttpStatus.CREATED);
                        } else {
                            throw new NotFoundException("User", "email", request.getUserEmail());
                        }
                    }
                    else {
                        lastPrices.setCurrent(true);
                        currentPrices.setCurrent(false);
                        this.tripRepository.save(new Trip(request, lastPrices.getPriceService()));
                        return new ResponseEntity(request.getId(), HttpStatus.CREATED);
                    }
                }
                throw new NotFoundException("GeneralPrices", "current", "true");
            }
            System.out.println("lanza esta exception");
            throw new NotFoundException("Scooter", "licensePlate", licensePlate);
        }

        throw new ConflictExistException("Trip", "ID", request.getId());
    }


    @Transactional
    public ResponseEntity finishTrip(FinishTripRequestDTO request, Long id) {
        Optional<Trip> tripExisting = this.tripRepository.findById(id);
        if(!tripExisting.isEmpty()){
            String licensePlate = tripExisting.get().getLicenseScooter();
            /*antes de finalizar el viaje debo chequear que mi scooter asociada
             * esta en una parada valida
             * solicitar a scooter-use-microservice (/api/scooters/{licensePlate}/in-stop
             *  )

            String token = this.request.getHeader(HttpHeaders.AUTHORIZATION);
            ScooterResponseDTO scooter = this.webClient.build()
                    .get()
                    .uri("http://scooter-use-microservice/api/scooters/{licensePlate}/in-stop", licensePlate)
                    .headers(httpHeaders -> {httpHeaders.set("Authorization", token);})
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ScooterResponseDTO.class)
                    .block();

             */

            tripExisting.get().setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            tripExisting.get().setKms(request.getKms());
            tripExisting.get().setEnded(request.getEnded());
            return new ResponseEntity(id, HttpStatus.ACCEPTED);

        }
        throw new NotFoundException("Trip", "Id", id);
    }

    @Transactional
    public ResponseEntity deleteTrip(Long id) {
        if(this.tripRepository.existsById(id)){
            this.tripRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }
    }

    @Transactional
    public ResponseEntity deleteAllTripByLicenseScooter(String licenseScooter) {
            this.tripRepository.deleteAllByLicenseScooter(licenseScooter);
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void initPause(Long id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(!trip.isEmpty()) {
            PauseTrip pauseTrip = trip.get().getPause();
            if(pauseTrip == null) {
                trip.get().setTimer(new TimerPause(trip.get().getId(), this.tripRepository, this.tariffRepository, this.priceRepository));
                trip.get().getTimer().initPause();
            }
            else {
                throw new ConflictExistException("PauseTrip", "Id", pauseTrip.getId());
            }
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }
    }

    @Transactional
    public ResponseEntity endPause(Long id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(!trip.isEmpty()) {
            PauseTrip pauseTrip = trip.get().getPause();
            if(pauseTrip != null) {
                if(pauseTrip.getEndPause() == null) {
                    Time endPause = Time.valueOf(LocalTime.now());
                    Time initPause = pauseTrip.getInitPause();
                    Long diff = endPause.getTime() - initPause.getTime();
                    pauseTrip.setTimePause(diff/1000);
                    pauseTrip.setEndPause(endPause);
                    this.tripRepository.save(trip.get());
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }
                else {
                    throw new PauseActiveException("PauseTrip", "id", pauseTrip.getId());
                }
            }
            else {
                throw new NotFoundException("PauseTrip", "Id", pauseTrip.getId());
            }
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }

    }

    @Transactional(readOnly = true)
    public List<ReportProfitsDTO> findProfitsBetweenMonthsInYear(TotalProfitsRequestDTO request) {
        return this.tariffRepository.findProfitsByMonthsInYear(request.getFirstMonth(), request.getLastMonth(), request.getYear())
                .stream().map(p->new ReportProfitsDTO(request.getFirstMonth(), request.getLastMonth(), p.getXyear(), p.getTotalProfits()))
                .collect(Collectors.toList());
    }

    public ResponseEntity saveNewPrices(GeneralPriceRequestDTO request) {
        if(this.priceRepository.findByDateValidity(request.getDateValidity()) == null) {
            this.priceRepository.save(new GeneralPrice(request));
            return new ResponseEntity("date validity: "+request.getDateValidity(), HttpStatus.CREATED);
        }

        throw new ConflictExistException("GeneralPrice", "dateValidity", request.getDateValidity());
    }

    @Transactional(readOnly = true)
    public List<GeneralPriceResponseDTO> findHistoryPrices() {
        List<GeneralPrice> prices = priceRepository.findAll();
        return prices.stream().map(s1-> new GeneralPriceResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScooterByTripsYearResponseDTO> findAllScooterByTripsAndYear(TripsAndYearRequestDTO request) {
        List<ScootersByTripsAndYearInterface> scooters =
                this.tripRepository.findAllScooterByTripsAndYear(request.getMinCountTrips(), request.getYear());
        return scooters.stream()
                .map(s1-> new ScooterByTripsYearResponseDTO(s1.getLicenseScooter(), s1.getCountTrips(), s1.getYear())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportScootersDTO> findUseScootersByKms() {
        System.out.println("entro al report kms");
        return this.tripRepository.findAllByKms()
                .stream()
                .map(r-> new ReportScootersDTO(r.getLicenseScooter(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportScootersDTO> findUseScootersByTimeCcPauses() {
        return this.tripRepository.findAllByTimeCcPauses()
                .stream()
                .map(r-> new ReportScootersDTO(r.getLicenseScooter(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportScootersDTO> findUseScootersByTimeOutPauses() {
        return this.tripRepository.findAllByTimeWithoutPauses()
                .stream()
                .map(r-> new ReportScootersDTO(r.getLicenseScooter(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
    }


}
