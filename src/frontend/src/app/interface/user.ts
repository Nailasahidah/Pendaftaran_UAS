export interface User {
  id: number;
  fullName: string;
  email: string;
  address?: string;
  phone: string;
  title?: string;
  bio?: string;
  imageUrl?: string;
  enabled: boolean;
  isNotLocked: boolean;
  usingMfa: boolean;
  createdAt?: Date;
  roleName: string;
  permissions: string;
}
