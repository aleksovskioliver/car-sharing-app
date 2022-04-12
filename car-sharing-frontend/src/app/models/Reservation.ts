import { MyLocation } from "./MyLocation";

export interface Reservation{
    id: number
    driverId: number,
    startTime: string,
    endTime: string,
    pickupLocation: MyLocation,
    dropoutLocation: MyLocation,
    tripCost: number,
    status: string
    availableSeats: number
}