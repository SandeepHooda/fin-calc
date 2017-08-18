import {  EventEmitter} from '@angular/core';

export class EventService {
    public refreshEvent: EventEmitter<String> = new EventEmitter();
   
    constructor() {}

    public refresh(): void {
        this.refreshEvent.emit("");
    }
}
