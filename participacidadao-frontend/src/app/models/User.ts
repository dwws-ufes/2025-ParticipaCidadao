import { Role } from "./enums/role.enum"

export default class Woman {

    public name?: string;
    public email?: string;
    public password?: string;
    public role: Role = Role.USER;
    public cpf?: string;
    public birthDate?: Date;

    constructor(object?: any) {
        if(object !== undefined) {
            this.updateUser(object);
        }
    }

    public updateUser(object: any) {
        this.name = object.name;
        this.email = object.email;
        this.password = object.password;
        this.cpf = object.cpf;
        this.birthDate = object.birthDate;
    }
}