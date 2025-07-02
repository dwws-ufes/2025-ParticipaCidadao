import { Component, OnInit } from '@angular/core';
import User from '../../../models/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] | null = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (response: any) => {
        this.users = response;
        console.log(this.users);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
