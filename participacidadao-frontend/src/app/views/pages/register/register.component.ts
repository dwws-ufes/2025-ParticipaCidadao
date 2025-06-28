import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import User from '../../../models/User';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';

// Custom validator for matching passwords
export function passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
  const password = control.get('password')?.value;
  const repeatPassword = control.get('repeatPassword')?.value;
  return password === repeatPassword ? null : { passwordMismatch: true };
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    cpf: new FormControl('', [Validators.required]),
    birthDate: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    repeatPassword: new FormControl('', [Validators.required])
  }, { validators: passwordMatchValidator });

  user?: User;

  constructor(private userService: UserService, private router: Router) { }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    console.log(this.registerForm.value);
    this.user = new User(this.registerForm.value);
    this.userService.register(this.user).subscribe({
      next: (response) => {
        this.registerForm.reset();
        console.log(response);
        // Redirect to login page after successful registration
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
