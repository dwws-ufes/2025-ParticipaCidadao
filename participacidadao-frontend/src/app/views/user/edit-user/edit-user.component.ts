
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import User from '../../../models/User';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  userForm!: FormGroup;
  submitted = false;
  successMessage = '';
  errorMessage = '';
  userId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Simulate getting user info from AuthService (should be replaced with real logic)
    const currentUser = this.authService.getCurrentUser();
    this.userId = currentUser?.id;
    this.userForm = this.fb.group({
      name: [currentUser?.name || '', Validators.required],
      email: [currentUser?.email || '', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      cpf: [currentUser?.cpf || '', Validators.required],
      birthDate: [currentUser?.birthDate ? this.formatDate(currentUser.birthDate) : '', Validators.required]
    });
  }

  formatDate(date: string | Date): string {
    // Format date as yyyy-MM-dd for input type="date"
    const d = new Date(date);
    return d.toISOString().substring(0, 10);
  }

  onSubmit(): void {
    this.submitted = true;
    this.successMessage = '';
    this.errorMessage = '';
    if (this.userForm.invalid || this.userId === null) {
      return;
    }
    const user: User = {
      ...this.userForm.value,
      id: this.userId
    };
    this.userService.updateUser(this.userId, user).subscribe({
      next: () => {
        this.successMessage = 'Dados atualizados com sucesso!';
      },
      error: (err) => {
        this.errorMessage = 'Erro ao atualizar dados: ' + (err.error?.message || 'Tente novamente.');
      }
    });
  }
}
