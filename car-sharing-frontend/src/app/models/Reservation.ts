export interface Reservation{
    driverId: number,
    startTime: string,
    endTime: string,
    pickupLocation: string,
    dropoutLocation: string,
    tripCost: number,
    availableSeats: number
}