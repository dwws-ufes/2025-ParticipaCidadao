import { Component } from '@angular/core';
import { IconDirective } from '@coreui/icons-angular';
import { ContainerComponent, RowComponent, ColComponent, TextColorDirective, CardComponent, CardBodyComponent, FormDirective, InputGroupComponent, InputGroupTextDirective, FormControlDirective, ButtonDirective } from '@coreui/angular';
import { User, UserService } from '../../../services/user.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    imports: [ContainerComponent, RowComponent, ColComponent, TextColorDirective, CardComponent, CardBodyComponent, FormDirective, InputGroupComponent, InputGroupTextDirective, IconDirective, FormControlDirective, ButtonDirective]
})
export class RegisterComponent {

  user: User = {
    name: '',
    email: '',
    password: '',
    cpf: '',
    birthDate: '',
    role: 'USER'
  };

  constructor(private userService: UserService) { }

  register() {
    this.userService.register(this.user).subscribe({
      next: () => alert('Cadastro realizado com sucesso!'),
      error: (err) => alert('Erro ao cadastrar: ' + err.message)
    });
  }

}
