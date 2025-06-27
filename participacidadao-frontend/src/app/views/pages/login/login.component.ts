import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
loginForm: FormGroup;
loginError: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

onSubmit() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: () => {
          // Handle successful login
          console.log('Conectado com sucesso');
        },
        error: (error) => {
          // Handle login error
          this.loginError = 'E-mail ou senha inválidos.';
          console.error('Login error:', error);
        }
      });
    } else {
      this.loginError = 'Por favor, preencha todos os campos.';
    }
  }

}
