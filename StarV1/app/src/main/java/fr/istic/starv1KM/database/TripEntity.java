package fr.istic.starv1KM.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents the table trip.
 *
 * @Version 1.0
 * @Author YEO KEVIN | BAMBA MOUSSA
 */
@Entity(tableName = StarContract.Trips.CONTENT_PATH)
public class TripEntity {

    @NonNull
    @PrimaryKey
    private String _id;

    @ColumnInfo(name = StarContract.Trips.TripColumns.ROUTE_ID)
    private String route_id;

    @ColumnInfo(name = StarContract.Trips.TripColumns.SERVICE_ID)
    private String service_id;

    @ColumnInfo(name = StarContract.Trips.TripColumns.HEADSIGN)
    private String trip_headsign;

    @ColumnInfo(name = StarContract.Trips.TripColumns.DIRECTION_ID)
    private String direction_id;

    @ColumnInfo(name = StarContract.Trips.TripColumns.BLOCK_ID)
    private String block_id;

    @ColumnInfo(name = StarContract.Trips.TripColumns.WHEELCHAIR_ACCESSIBLE)
    private String wheelchair_accessible;


    public TripEntity(@NonNull String _id, String route_id, String service_id, String trip_headsign, String direction_id, String block_id, String wheelchair_accessible) {
        this.route_id = route_id;
        this.service_id = service_id;
        this._id = _id;
        this.trip_headsign = trip_headsign;
        this.direction_id = direction_id;
        this.block_id = block_id;
        this.wheelchair_accessible = wheelchair_accessible;
    }


    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }


    public String getService_id() {
        return service_id;
    }


    public void setService_id(String service_id) {
        this.service_id = service_id;
    }


    public String get_id() {
        return _id;
    }


    public void set_id(String _id) {
        this._id = _id;
    }


    public String getTrip_headsign() {
        return trip_headsign;
    }


    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }


    public String getDirection_id() {
        return direction_id;
    }


    public void setDirection_id(String direction_id) {
        this.direction_id = direction_id;
    }


    public String getBlock_id() {
        return block_id;
    }


    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }


    public String getWheelchair_accessible() {
        return wheelchair_accessible;
    }


    public void setWheelchair_accessible(String wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }
}
