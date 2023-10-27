package Service.GrpcServicesIMPL;


import com.maintenance.grpc.MaintenanceServiceGrpc;
import com.maintenance.grpc.ScooterListResponse;
import com.maintenance.grpc.number;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceGRPCImpl extends MaintenanceServiceGrpc.MaintenanceServiceImplBase {
    @Override
    public void getScooterForKMS(number request, StreamObserver<ScooterListResponse> responseObserver) {
        ScooterListResponse scooter
    }
}
