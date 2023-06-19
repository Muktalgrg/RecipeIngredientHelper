import { FileDB } from "./FileDB";
import { Ingredient } from "./ingredient";


export class Recipe {
    id: number;
    name: string;
    ingredients: Ingredient[];
    description: string;
    fileDB: FileDB;
}

