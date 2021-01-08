package hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;

public interface VehicleSelectionListener extends Serializable {

    void onVehicleSelected(VehicleBean vehicleBean);
}
