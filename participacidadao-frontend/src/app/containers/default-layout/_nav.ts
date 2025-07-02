import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Usuário',
    url: '/usuario',
    iconComponent: { name: 'cilUser' },
    children: [
      {
        name: 'Usuários cadastrados',
        url: '/usuario/gerenciar'
      },
      {
        name: 'Meus dados',
        url: '/usuario/dados'
      }
    ]
  },
  {
    name: 'Problemas Urbanos',
    url: '/problema',
    iconComponent: { name: 'cilClipboard' },
    children: [
      {
        name: 'Novo problema',
        url: '/problema/novo'
      }
    ]
  }
];
