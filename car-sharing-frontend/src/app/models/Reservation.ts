import { MyLocation } from "./MyLocation";

export interface Reservation{
    driverId: number,
    startTime: string,
    endTime: string,
    pickupLocation: MyLocation,
    dropoutLocation: MyLocation,
    tripCost: number,
    availableSeats: number
}