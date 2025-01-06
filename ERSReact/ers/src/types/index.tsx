export interface Reimbursement {
    reimId: number;
    description: string;
    amount: number;
    status: string;
    employeeName?: string;
}
  
export interface DecodedToken {
    firstname: string;
    lastname: string;
    role: string;
}