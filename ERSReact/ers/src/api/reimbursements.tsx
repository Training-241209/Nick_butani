import axios from 'axios';
import { Reimbursement } from '../types';

const BASE_URL = 'http://localhost:8080';

export const reimbursementApi = {
  getMyReimbursements: async (token: string) => {
    const response = await axios.get<Reimbursement[]>(`${BASE_URL}/reimburse/me`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
  },

  getAllReimbursements: async (token: string) => {
    const response = await axios.get<Reimbursement[]>(`${BASE_URL}/reimburse/all`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
  },

  createReimbursement: async (token: string, data: { description: string; amount: string }) => {
    const response = await axios.post<Reimbursement>(`${BASE_URL}/reimburse/create`, data, {
      headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
  },

  updateStatus: async (token: string, reimId: number, status: 'approve' | 'deny') => {
    const response = await axios.patch(
      `${BASE_URL}/reimburse/${reimId}/${status}`,
      {},
      { headers: { Authorization: `Bearer ${token}` }}
    );
    return response.data;
  }
};