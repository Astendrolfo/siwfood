export interface Ricetta {
  id? : number;
  authorId: number;
  authorName: string;
  description: string;
  immagine: string;
  listaIngredienti: any[];
  title: string;
  immagineUrl?: string;
}


