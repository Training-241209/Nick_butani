import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Box, useTheme } from '@mui/material';
import { useState } from 'react';
import { tokens } from '@/theme';

interface Props {
  open: boolean;
  onClose: () => void;
  onSubmit: (data: { description: string; amount: string }) => void;
}

export const CreateReimbursementDialog = ({ open, onClose, onSubmit }: Props) => {
  const [value, setValue] = useState({ description: '', amount: '' });
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(value);
    setValue({ description: '', amount: '' });
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent style={{
        backgroundColor: colors.primary[400],
        color: colors.grey[100]
      }}>
        <DialogHeader>
          <DialogTitle>Create Reimbursement</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <Box display="flex" flexDirection="column" gap="16px">
            <Box>
              <Label htmlFor="description">Description</Label>
              <Input
                id="description"
                name="description"
                value={value.description}
                onChange={(e) => setValue(prev => ({ ...prev, description: e.target.value }))}
                required
              />
            </Box>
            <Box>
              <Label htmlFor="amount">Amount</Label>
              <Input
                id="amount"
                name="amount"
                type="number"
                value={value.amount}
                onChange={(e) => setValue(prev => ({ ...prev, amount: e.target.value }))}
                required
              />
            </Box>
          </Box>
          <DialogFooter style={{marginTop:"20px"}}>
            <Button type="button" variant="outline" onClick={onClose}   
            style={{
                color: colors.grey[500],
                borderColor: colors.grey[500],
            }} >Cancel</Button>
            <Button type="submit"
            style={{backgroundColor:colors.blueAccent[700], color:colors.grey[100]}}
            >Submit</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
};