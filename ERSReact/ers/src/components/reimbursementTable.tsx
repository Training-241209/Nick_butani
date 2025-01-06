import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Box, Typography } from '@mui/material';
import { Reimbursement } from '../types';
import { useTheme } from '@mui/material';
import { tokens } from '../theme';
import { Button } from './ui/button';

interface Props {
  reimbursements: Reimbursement[];
  isManager?: boolean;
  onApprove?: (id: number) => void;
  onDeny?: (id: number) => void;
}

export const ReimbursementTable = ({ reimbursements, isManager, onApprove, onDeny }: Props) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const baseColumns: GridColDef[] = [
    { field: 'reimId', headerName: 'ID', width: 90 },
    { field: 'description', headerName: 'Description', flex: 1 },
    { 
      field: 'amount', 
      headerName: 'Amount', 
      flex: 1,
      renderCell: (params) => `$${params.row.amount.toFixed(2)}`
    },
    {
      field: 'status',
      headerName: 'Status',
      flex:1,
      renderCell: (params) => (
        <Box
          sx={{
            backgroundColor: 
              params.value === 'APPROVED' ? colors.greenAccent[700] :
              params.value === 'DENIED' ? colors.redAccent[700] :
              colors.grey[500],
            padding: '5px',
            borderRadius: '4px',
            width: '40%',
            display: 'flex',
            justifyContent: 'center',
            marginTop:"10px"
          }}
        >
          <Typography color={colors.grey[100]}>{params.value}</Typography>
        </Box>
      )
    }
  ];

  const managerColumns: GridColDef[] = isManager ? [
    ...baseColumns,
    { field: 'employeeName', headerName: 'Employee', width: 200 },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 200,
      renderCell: (params) => 
        params.row.status === 'PENDING' && (
          <Box display="flex" gap={1}>
            <Button onClick={() => onApprove?.(params.row.reimId)}>Approve</Button>
            <Button onClick={() => onDeny?.(params.row.reimId)}>Deny</Button>
          </Box>
        )
    }
  ] : baseColumns;

  return (
    <DataGrid
      rows={reimbursements}
      columns={managerColumns}
      getRowId={(row) => row.reimId}
      sx={{
        '& .MuiDataGrid-root': { border: 'none' },
        '& .MuiDataGrid-cell': { borderBottom: 'none' },
        '& .name-column--cell': { color: colors.greenAccent[300] },
        '& .MuiDataGrid-columnHeader': {
          backgroundColor: colors.blueAccent[700],
          borderBottom: 'none'
        },
        '& .MuiDataGrid-virtualScroller': {
          backgroundColor: colors.primary[400]
        },
        '& .MuiDataGrid-footerContainer': {
          borderTop: 'none',
          backgroundColor: colors.blueAccent[700]
        }
      }}
    />
  );
};
