import { reimbursementApi } from "@/api/reimbursements";
import { CreateReimbursementDialog } from "@/components/createReimbursementDialog";
import Header from "@/components/header";
import { ReimbursementTable } from "@/components/reimbursementTable";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/hooks/useAuth";
import { Reimbursement } from "@/types";
import { Box } from "@mui/material";
import { useEffect, useState } from "react";

const ReimbursementsPage = () => {
    const { user } = useAuth();
    const [reimbursements, setReimbursements] = useState<Reimbursement[]>([]);
    const [dialogOpen, setDialogOpen] = useState(false);
  
    useEffect(() => {
      const token = localStorage.getItem('token');
      if (token) {
        const fetchData = async () => {
          const data = await reimbursementApi.getMyReimbursements(token);
          setReimbursements(data);
        };
        fetchData();
      }
    }, []);
  
    const handleCreate = async (data: { description: string; amount: string }) => {
      const token = localStorage.getItem('token');
      if (token) {
        const newReimbursement = await reimbursementApi.createReimbursement(token, data);
        setReimbursements(prev => [...prev, newReimbursement]);
        setDialogOpen(false);
      }
    };
  
    return (
      <Box m="20px">
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Header title="REIMBURSEMENTS" subtitle="Manage your reimbursements" />
          <Button onClick={() => setDialogOpen(true)}>Create New</Button>
        </Box>
        
        <ReimbursementTable 
          reimbursements={reimbursements}
          isManager={user?.role === 'MANAGER'}
        />
  
        <CreateReimbursementDialog
          open={dialogOpen}
          onClose={() => setDialogOpen(false)}
          onSubmit={handleCreate}
        />
      </Box>
    );
  };

  export default ReimbursementsPage;