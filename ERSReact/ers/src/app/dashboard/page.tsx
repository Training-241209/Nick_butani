import { Box, useTheme } from "@mui/material";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import PaidOutlinedIcon from "@mui/icons-material/PaidOutlined";
import PendingActionsOutlinedIcon from "@mui/icons-material/PendingActionsOutlined";
import PersonIcon from "@mui/icons-material/Person";
import { tokens } from "@/theme";
import Header from "@/components/header";
import StatBox from "@/components/statbox";
import { Button } from "@/components/ui/button";
import { ReimbursementTable } from "@/components/reimbursementTable";
import { useAuth } from "@/hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { reimbursementApi } from "@/api/reimbursements";
import { Reimbursement } from "@/types";

const Dashboard = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const navigate = useNavigate();
  const { user } = useAuth();

  const [reimbursements, setReimbursements] = useState<Reimbursement[]>([]);
  const [summary, setSummary] = useState({
    totalReimbursements: 0,
    totalAmount: 0,
    totalPending: 0,
  });

  const isManager = user?.role === "MANAGER";

  useEffect(() => {
    const fetchReimbursements = async () => {
      const token = localStorage.getItem("token");
      if (!token) return;

      try {
        const data = isManager 
          ? await reimbursementApi.getAllReimbursements(token)
          : await reimbursementApi.getMyReimbursements(token);

        setReimbursements(data);
        setSummary({
          totalReimbursements: data.length,
          totalAmount: data.reduce((sum, item) => sum + item.amount, 0),
          totalPending: data.filter((item) => item.status === "PENDING").length,
        });
      } catch (error) {
        console.error("Error fetching reimbursements:", error);
      }
    };

    fetchReimbursements();
  }, [isManager]);

  const handleApprove = async (reimId: number) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      await reimbursementApi.updateStatus(token, reimId, "approve");
      const data = await reimbursementApi.getAllReimbursements(token);
      setReimbursements(data);
    } catch (error) {
      console.error("Error approving reimbursement:", error);
    }
  };

  const handleDeny = async (reimId: number) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      await reimbursementApi.updateStatus(token, reimId, "deny");
      const data = await reimbursementApi.getAllReimbursements(token);
      setReimbursements(data);
    } catch (error) {
      console.error("Error denying reimbursement:", error);
    }
  };

  return (
    <Box m="20px">
      <Box display="flex" justifyContent="space-between" alignItems="center">
        <Header 
          title={isManager ? "MANAGER DASHBOARD" : "DASHBOARD"}
          subtitle={`Welcome ${user?.firstname}`} 
        />
        {isManager && (
          <Button
            onClick={() => navigate('/users')}
          >
            <PersonIcon className="mr-2 h-4 w-4" />
            View Employees
          </Button>
        )}
      </Box>

      <Box
        display="grid"
        gridTemplateColumns="repeat(12, 1fr)"
        gridAutoRows="140px"
        gap="20px"
        mb="20px"
      >
        <Box gridColumn="span 4" sx={{
          backgroundColor: colors.primary[400],
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}>
          <StatBox
            title={summary.totalReimbursements.toString()}
            subtitle="Total Reimbursements"
            icon={<ReceiptLongOutlinedIcon sx={{ color: colors.greenAccent[600], fontSize: "26px" }} />}
          />
        </Box>

        <Box gridColumn="span 4" sx={{
          backgroundColor: colors.primary[400],
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}>
          <StatBox
            title={`$${summary.totalAmount.toFixed(2)}`}
            subtitle="Total Amount"
            icon={<PaidOutlinedIcon sx={{ color: colors.greenAccent[600], fontSize: "26px" }} />}
          />
        </Box>

        <Box gridColumn="span 4" sx={{
          backgroundColor: colors.primary[400],
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}>
          <StatBox
            title={summary.totalPending.toString()}
            subtitle="Pending Reimbursements"
            icon={<PendingActionsOutlinedIcon sx={{ color: colors.greenAccent[600], fontSize: "26px" }} />}
          />
        </Box>
      </Box>

      <Box height="calc(100vh - 400px)">
        <ReimbursementTable 
          reimbursements={reimbursements}
          isManager={isManager}
          onApprove={handleApprove}
          onDeny={handleDeny}
        />
      </Box>
    </Box>
  );
};

export default Dashboard;