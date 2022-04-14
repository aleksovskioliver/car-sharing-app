import { ReservationDTO } from "./ReservationDTO";
import { Vehicle } from "./Vehicle";

export interface User {
    firstName: string;
    lastName: string;
    phoneNumber: string;
    email: string;
    password: string;
    role: string;
    vehicle: Vehicle | null;
    reservation: ReservationDTO[] | null
}