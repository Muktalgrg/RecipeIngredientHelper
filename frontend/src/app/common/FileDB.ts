export class FileDB{
    id: number;
    name: String;
    type: string;
    data: any;

    constructor(name: string, type: string, data: any){
        this.name = name;
        this.type = type;
        this.data = data;

    }

}

