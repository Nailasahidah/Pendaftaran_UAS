import {Invoice} from "./invoice";
export interface Product {
  id: number;
  name: string;
  price: string;
  stock: string;
  imageUrl?: string;
  createdAt: Date;
  invoices?: Invoice[];
}
