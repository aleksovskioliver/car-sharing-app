import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User | null = null
  error: string = ''

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe({
      next: resp => {
        if (resp.user) {
          this.user = resp.user
        }
        if (resp.error) {
          this.error = resp.error
        }
      },
      error: error => console.log(error)
    })
  }

}
