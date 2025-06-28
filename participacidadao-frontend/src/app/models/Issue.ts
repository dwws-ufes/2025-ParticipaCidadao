import { Status } from "./enums/status.enum";

export default class Issue {

    public title?: string;
    public description?: string;
    public imageUrl: string = '';
    public status: Status = Status.REPORTADO;
    public createdAt: Date = new Date();
    public street?: string;
    public city?: string;
    public neighborhood?: string;
    public refPoint?: string;
    public createdBy?: string;

    constructor(object?: any) {
        if(object !== undefined) {
            this.updateIssue(object);
        }
    }

    public updateIssue(object: any) {
        this.title = object.title;
        this.description = object.description;
        this.street = object.street;
        this.city = object.city;
        this.neighborhood = object.neighborhood;
        this.refPoint = object.refPoint;
        this.createdBy = object.createdBy;
    }
}