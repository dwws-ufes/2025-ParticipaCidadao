import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import User from '../../../models/User';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {


  registerForm = new FormGroup({
    name: new FormControl(''),
    email: new FormControl(''),
    cpf: new FormControl(''),
    birthDate: new FormControl(''),
    password: new FormControl(''),
    repeatPassword: new FormControl('')
  });

  user?:User

  constructor(private userService: UserService) { }

  onSubmit() {
    console.log(this.registerForm.value);
    this.user = new User(this.registerForm.value);
    
    this.userService.register(this.user).subscribe({
      next: (response) => {
        this.registerForm.reset();
        console.log(response);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

}
