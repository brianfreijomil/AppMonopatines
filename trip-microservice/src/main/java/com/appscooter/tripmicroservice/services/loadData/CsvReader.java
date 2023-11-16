package com.appscooter.tripmicroservice.services.loadData;

import com.appscooter.tripmicroservice.domain.GeneralPrice;
import com.appscooter.tripmicroservice.domain.Trip;
import com.appscooter.tripmicroservice.repositories.GeneralPriceRepository;
import com.appscooter.tripmicroservice.repositories.TariffRepository;
import com.appscooter.tripmicroservice.repositories.TripRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CsvReader {
    private TripRepository tripRepository;
    private TariffRepository tariffRepository;
    private GeneralPriceRepository pricesRepository;
    private static final String userDir =
            System.getProperty("user.dir") + "/src/main/java/com/appscooter/tripmicroservice/services/loadData/";

    @Autowired
    public CsvReader(TripRepository tr, TariffRepository tariffRepository,
                     GeneralPriceRepository pr) throws IOException, SQLException {
        this.tripRepository = tr;
        this.tariffRepository = tariffRepository;
        this.pricesRepository = pr;
    }

    public void load() throws SQLException, IOException {
        this.loadPrices();
        this.loadTrip();
    }

    private void loadPrices() throws IOException, SQLException {
        GeneralPrice price = new GeneralPrice(198.5, 30.5, true, Timestamp.valueOf(LocalDateTime.now()));
        this.pricesRepository.save(price);
    }

    private void loadTrip() throws IOException, SQLException {
        CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new
                FileReader(userDir + "trip.csv"));
        for (CSVRecord row : parser) {
            Long id = Long.valueOf(row.get("id"));
            Timestamp initTime = Timestamp.valueOf(row.get("init_time"));
            Timestamp endTime = Timestamp.valueOf(row.get("end_time"));
            Double kms = Double.valueOf(row.get("kms"));
            Boolean ended = Boolean.valueOf(row.get("ended"));
            String scooter = String.valueOf(row.get("licenseScooter"));
            Long user = Long.valueOf(row.get("user"));
            Trip trip = new Trip(id, initTime, endTime, kms, ended, 198.5, scooter, user);
            tripRepository.save(trip);
        }
    }

}
