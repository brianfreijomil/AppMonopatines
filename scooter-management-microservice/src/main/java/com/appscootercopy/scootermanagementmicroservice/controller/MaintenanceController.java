import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    public MaintenanceController() {}

    @GetMapping("/")
    public ResponseEntity hello(){
        return new ResponseEntity("bienm",HttpStatus.OK);
    }

    @PutMapping("/{licensePlate}/setMaintenance")
    public ResponseEntity setMaintenance(@PathVariable String licensePlate, @RequestBody boolean setMaintenance) {
        System.out.println(licensePlate);
        System.out.println(setMaintenance);
        return new ResponseEntity(HttpStatus.OK);
    }
}
