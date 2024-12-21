export interface Product {
  id: number;
  title: string;
  description: string;
  thumbnail: string;
}

export interface MarketData {
  id: number;
  ask: string;
  bid: string;
  price: string;
  favorite: boolean;
}
